package com.nvshink.winterarc.data.local.trainingplanexercise.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.nvshink.winterarc.data.local.trainingplanexercise.entity.TrainingPlanExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingPlanExerciseDao {
    @Upsert
    suspend fun upsertTrainingPlanExercise(trainingPlanExercise: TrainingPlanExercise)

    @Delete
    suspend fun deleteTrainingPlanExercise(trainingPlanExercise: TrainingPlanExercise)

    @Query("SELECT * FROM training_plan_exercise WHERE training_plan_id_foreign_key = :trainingPlanId")
    fun getTrainingPlanExercisesByTrainingPlanId(trainingPlanId: Long): Flow<List<TrainingPlanExercise>>

    @Query("SELECT * FROM training_plan_exercise WHERE training_plan_exercise_id = :id")
    fun getTrainingPlanExercisesById(id: Long): Flow<TrainingPlanExercise>
}