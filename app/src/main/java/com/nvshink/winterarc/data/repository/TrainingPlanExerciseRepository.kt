package com.nvshink.winterarc.data.repository

import com.nvshink.winterarc.data.model.TrainingPlan
import com.nvshink.winterarc.data.model.TrainingPlanExercise
import com.nvshink.winterarc.data.room.ExerciseDao
import com.nvshink.winterarc.data.room.TrainingPlanExerciseDao
import kotlinx.coroutines.flow.Flow

class TrainingPlanExerciseRepository(
    private val dao: TrainingPlanExerciseDao
) {
    suspend fun upsertTrainingPlan(trainingPlanExercise: TrainingPlanExercise) = dao.upsertTrainingPlanExercise(trainingPlanExercise = trainingPlanExercise)
    suspend fun deleteTrainingPlan(trainingPlanExercise: TrainingPlanExercise) = dao.deleteTrainingPlanExercise(trainingPlanExercise = trainingPlanExercise)
    suspend fun getTrainingPlansByName(): Flow<MutableMap<Int, TrainingPlanExercise>> = dao.getTrainingPlanExerciseByName()
}