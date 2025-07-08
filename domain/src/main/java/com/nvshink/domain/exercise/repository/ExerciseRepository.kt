package com.nvshink.domain.exercise.repository

import com.nvshink.domain.exercise.model.ExerciseModel
import com.nvshink.domain.resouce.Resource
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    /**
     * Insert an exercise in Realm DB or update, if it has already been inserted.
     * @param exercise An exercise that is being recorded or updated in the DB.
     */
    suspend fun upsertExercise(exercise: ExerciseModel)

    /**
     * Delete an exercise in Realm DB.
     * @param exercise An exercise that is being deleted from the DB.
     */
    suspend fun deleteExercise(exercise: ExerciseModel)

    /**
     * @return Flow with List sorted by name in ascending order.
     */
    fun getExercisesByNameASC(): Flow<Resource<List<ExerciseModel>>>

    /**
     * @return Flow with List sorted by name in descending order.
     */
    fun getExercisesByNameDESC(): Flow<Resource<List<ExerciseModel>>>

    /**
     * @return Flow with exercise by id.
     */
    fun getExerciseById(id: Long): Flow<Resource<ExerciseModel>>



}