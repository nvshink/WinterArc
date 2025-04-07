package com.nvshink.winterarc.data.repository

import com.nvshink.winterarc.data.Datasource
import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.data.model.TrainingPlan
import com.nvshink.winterarc.data.room.ExerciseDao
import com.nvshink.winterarc.data.room.TrainingPlanDao
import kotlinx.coroutines.flow.Flow

class TrainingPlanRepository(
    private val dao: TrainingPlanDao
) {
    suspend fun upsertTrainingPlan(trainingPlan: TrainingPlan) = dao.upsertTrainingPlan(trainingPlan = trainingPlan)
    suspend fun deleteTrainingPlan(trainingPlan: TrainingPlan) = dao.deleteTrainingPlan(trainingPlan = trainingPlan)
    suspend fun getTrainingPlansByName(): Flow<MutableMap<Int, TrainingPlan>> = dao.getTrainingPlanByName()
    suspend fun getTrainingPlanWithTrainingPlanExercises() = dao.getTrainingPlanWithTrainingPlanExercises()
}