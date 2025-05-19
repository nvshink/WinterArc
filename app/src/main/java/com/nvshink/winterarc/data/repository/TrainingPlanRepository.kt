package com.nvshink.winterarc.data.repository

import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.data.model.TrainingPlan
import com.nvshink.winterarc.data.model.TrainingPlanExercise
import com.nvshink.winterarc.data.room.TrainingPlanDao
import com.nvshink.winterarc.data.room.TrainingPlanExerciseDao
import kotlinx.coroutines.flow.Flow

class TrainingPlanRepository(
    private val trainingPlanDao: TrainingPlanDao,
    private val trainingPlanExerciseDao: TrainingPlanExerciseDao
    ) {
    /**
     * Insert an training plan in Realm DB or update, if it has already been inserted.
     * @param trainingPlan An training plan that is being recorded or updated in the DB.
     */
    suspend fun upsertTrainingPlan(trainingPlan: TrainingPlan): Long {
        return trainingPlanDao.upsertTrainingPlan(trainingPlan = trainingPlan)
    }

    /**
     * Delete an training plan in Realm DB.
     * @param trainingPlan An training plan that is being deleted from the DB.
     */
    suspend fun deleteTrainingPlan(trainingPlan: TrainingPlan) =
        trainingPlanDao.deleteTrainingPlan(trainingPlan = trainingPlan)

    /**
     * @return Flow with liat sorted by name in ascending order.
     */
    fun getTrainingPlansByNameASC(): Flow<List<TrainingPlan>> =
        trainingPlanDao.getTrainingPlanByNameASC()

    /**
     * @return Flow with liat sorted by name in descending order.
     */
    fun getTrainingPlansByNameDESC(): Flow<List<TrainingPlan>> =
        trainingPlanDao.getTrainingPlanByNameDESC()


    /**
     * Insert an training plan exercise in Realm DB or update, if it has already been inserted.
     * @param trainingPlanExercise An training plan that is being recorded or updated in the DB.
     */
    private suspend fun upsertTrainingPlanExercise(trainingPlanExercise: TrainingPlanExercise) =
        trainingPlanExerciseDao.upsertTrainingPlanExercise(trainingPlanExercise = trainingPlanExercise)

    /**
     * Delete an training plan in Realm DB.
     * @param trainingPlanExercise An training plan exercise that is being deleted from the DB.
     */
    suspend fun deleteTrainingPlanExercise(trainingPlanExercise: TrainingPlanExercise) =
        trainingPlanExerciseDao.deleteTrainingPlanExercise(trainingPlanExercise = trainingPlanExercise)

    /**
     * Based on the training plan and the list of exercises and their parameters, a training plan is created and written to the DB.
     * @param trainingPlan A training plan in which the exercises are filled out.
     * @param exercisesWithParams List of pair exercises and params.
     */
    suspend fun createTrainingPlanWithExercises(
        trainingPlan: TrainingPlan,
        exercisesWithParams: List<Pair<Exercise, TrainingPlanExerciseParams>>
    ) {
        val planId = upsertTrainingPlan(trainingPlan)

        exercisesWithParams.forEach { (exercise, params) ->
            val trainingPlanExercise = TrainingPlanExercise(
                trainingPlanIdForeignKey = planId,
                exerciseIdForeignKey = exercise.id,
                duration = params.duration,
                isInSets = params.isInSets,
            )
            upsertTrainingPlanExercise(trainingPlanExercise)
        }

    }

    /**
     * Supported class to contain exercise params
     * @param duration Exercise duration. Use for sets and time exercises.
     * @param isInSets Define duration as sets or as seconds
     */
    data class TrainingPlanExerciseParams(
        val duration: Int,
        val isInSets: Boolean,
    )
}
