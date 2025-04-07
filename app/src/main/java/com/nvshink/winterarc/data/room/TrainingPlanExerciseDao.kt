package com.nvshink.winterarc.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.MapColumn
import androidx.room.Query
import androidx.room.Upsert
import com.nvshink.winterarc.data.model.TrainingPlanExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingPlanExerciseDao {
    @Upsert
    fun upsertTrainingPlanExercise(trainingPlanExercise: TrainingPlanExercise)

    @Delete
    fun deleteTrainingPlanExercise(trainingPlanExercise: TrainingPlanExercise)

    @Query("SELECT * FROM training_plan_exercise")
    fun getTrainingPlanExerciseByName(): Flow<MutableMap<@MapColumn(columnName = "training_plan_exercise_id") Int, TrainingPlanExercise>>
}