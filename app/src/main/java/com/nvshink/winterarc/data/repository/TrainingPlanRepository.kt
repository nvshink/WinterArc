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
    suspend fun upsertTrainingPlan(trainingPlan: TrainingPlan): Long {
        return trainingPlanDao.upsertTrainingPlan(trainingPlan = trainingPlan)
    }

    suspend fun deleteTrainingPlan(trainingPlan: TrainingPlan) =
        trainingPlanDao.deleteTrainingPlan(trainingPlan = trainingPlan)

    fun getTrainingPlansByName(): Flow<MutableMap<Int, TrainingPlan>> =
        trainingPlanDao.getTrainingPlanByName()

    fun getTrainingPlanWithTrainingPlanExercises() =
        trainingPlanDao.getTrainingPlanWithTrainingPlanExercises()

    private suspend fun upsertTrainingPlanExercise(trainingPlanExercise: TrainingPlanExercise) =
        trainingPlanExerciseDao.upsertTrainingPlanExercise(trainingPlanExercise = trainingPlanExercise)

    suspend fun deleteTrainingPlanExercise(trainingPlanExercise: TrainingPlanExercise) =
        trainingPlanExerciseDao.deleteTrainingPlanExercise(trainingPlanExercise = trainingPlanExercise)

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

    data class TrainingPlanExerciseParams(
        val duration: Int,
        val isInSets: Boolean,
    )
}
