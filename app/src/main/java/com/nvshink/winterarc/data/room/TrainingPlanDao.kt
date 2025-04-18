package com.nvshink.winterarc.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.MapColumn
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.nvshink.winterarc.data.model.TrainingPlan
import com.nvshink.winterarc.data.model.TrainingPlanWithTrainingPlanExercises
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingPlanDao {
    @Upsert
    suspend fun upsertTrainingPlan(trainingPlan: TrainingPlan): Long

    @Delete
    suspend fun deleteTrainingPlan(trainingPlan: TrainingPlan)

    @Query("SELECT * FROM training_plan ORDER BY name ASC")
    fun getTrainingPlanByName(): Flow<MutableMap<@MapColumn(columnName = "training_plan_id")Int, TrainingPlan>>

    @Transaction
    @Query("SELECT * FROM training_plan")
    fun getTrainingPlanWithTrainingPlanExercises(): TrainingPlanWithTrainingPlanExercises
}