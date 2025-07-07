package com.nvshink.data.local.exercise.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.nvshink.data.local.exercise.entity.ExerciseEntity
import com.nvshink.data.local.exercise.relation.ExerciseWithTrainingPlanExercises
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Upsert
    suspend fun upsertExercise(exercise: ExerciseEntity)

    @Delete
    suspend fun deleteExercise(exercise: ExerciseEntity)

    @Query("SELECT * FROM exercise ORDER BY name ASC")
    fun getExercisesByNameASC(): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM exercise ORDER BY name DESC")
    fun getExercisesByNameDESC(): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM exercise WHERE exercise_id =:id")
    fun getExercisesById(id: Long): Flow<ExerciseEntity>


    @Transaction
    @Query("SELECT * FROM exercise")
    fun getExerciseWithTrainingPlanExercises(): ExerciseWithTrainingPlanExercises
}

