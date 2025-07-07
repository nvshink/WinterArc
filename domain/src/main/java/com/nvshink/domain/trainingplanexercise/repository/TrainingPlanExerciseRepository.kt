package com.nvshink.domain.trainingplanexercise.repository

import com.nvshink.domain.trainingplan.model.TrainingPlanModel
import com.nvshink.domain.trainingplanexercise.model.TrainingPlanExerciseModel
import kotlinx.coroutines.flow.Flow

interface TrainingPlanExerciseRepository {

    /**
     * Insert an training plan exercise in Realm DB or update, if it has already been inserted.
     * @param trainingPlanExercise An training plan that is being recorded or updated in the DB.
     */
    suspend fun upsertTrainingPlanExercise(trainingPlanExercise: TrainingPlanExerciseModel)

    /**
     * Based on the training plan and the list of exercises and their parameters, a training plan is created and written to the DB.
     * @param trainingPlanExercises A list of training plan exercises.
     */
    suspend fun upsertListOfTrainingPlanWithExercises(trainingPlanExercises: List<TrainingPlanExerciseModel>)

    /**
     * Delete an training plan in Realm DB.
     * @param trainingPlanExercise An training plan exercise that is being deleted from the DB.
     */
    suspend fun deleteTrainingPlanExercise(trainingPlanExercise: TrainingPlanExerciseModel)

    /**
     * @return Flow with list training plan exercises.
     */
    fun getTrainingPlanExercisesByTrainingPlanId(trainingPlanId: Long): Flow<List<TrainingPlanExerciseModel>>

    /**
     * @return Flow with training plan exercise.
     */
    fun getTrainingPlanExercisesById(id: Long): Flow<TrainingPlanExerciseModel>

}