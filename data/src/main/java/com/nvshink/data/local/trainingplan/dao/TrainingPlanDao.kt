package com.nvshink.data.local.trainingplan.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.nvshink.data.local.trainingplan.entity.TrainingPlanEntity
import com.nvshink.data.local.trainingplan.relation.TrainingPlanWithTrainingPlanExercises
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingPlanDao {
    @Upsert
    suspend fun upsertTrainingPlan(trainingPlan: TrainingPlanEntity): Long

    @Delete
    suspend fun deleteTrainingPlan(trainingPlan: TrainingPlanEntity)

    @Query("SELECT * FROM training_plan ORDER BY name ASC")
    fun getTrainingPlanByNameASC(): Flow<List<TrainingPlanEntity>>

    @Query("SELECT * FROM training_plan ORDER BY name DESC")
    fun getTrainingPlanByNameDESC(): Flow<List<TrainingPlanEntity>>

    @Transaction
    @Query("SELECT * FROM training_plan")
    fun getTrainingPlanWithTrainingPlanExercises(): TrainingPlanWithTrainingPlanExercises
}