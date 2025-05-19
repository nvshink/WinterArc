package com.nvshink.winterarc.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.data.model.ExerciseWithTrainingPlanExercises
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Upsert
    suspend fun upsertExercise(exercise: Exercise)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    @Query("SELECT * FROM exercise ORDER BY name ASC")
    fun getExercisesByNameASC(): Flow<List<Exercise>>

    @Query("SELECT * FROM exercise ORDER BY name DESC")
    fun getExercisesByNameDESC(): Flow<List<Exercise>>


    @Transaction
    @Query("SELECT * FROM exercise")
    fun getExerciseWithTrainingPlanExercises(): ExerciseWithTrainingPlanExercises
}

