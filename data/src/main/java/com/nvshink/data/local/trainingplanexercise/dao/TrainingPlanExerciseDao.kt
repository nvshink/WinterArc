package com.nvshink.data.local.trainingplanexercise.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.nvshink.data.local.trainingplanexercise.entity.TrainingPlanExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingPlanExerciseDao {
    @Upsert
    suspend fun upsertTrainingPlanExercise(trainingPlanExercise: TrainingPlanExerciseEntity)

    @Delete
    suspend fun deleteTrainingPlanExercise(trainingPlanExercise: TrainingPlanExerciseEntity)

    @Query("SELECT * FROM training_plan_exercise WHERE training_plan_id_foreign_key = :trainingPlanId")
    fun getTrainingPlanExercisesByTrainingPlanId(trainingPlanId: Long): Flow<List<TrainingPlanExerciseEntity>>

    @Query("SELECT * FROM training_plan_exercise WHERE training_plan_exercise_id = :id")
    fun getTrainingPlanExercisesById(id: Long): Flow<TrainingPlanExerciseEntity>
}