package com.nvshink.winterarc.data.repository

import com.nvshink.winterarc.data.Datasource
import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.data.room.ExerciseDao
import com.nvshink.winterarc.data.room.WinterArcDatabase
import kotlinx.coroutines.flow.Flow

class ExerciseRepository(
    private val dao: ExerciseDao
) {
    suspend fun upsertExercise(exercise: Exercise) = dao.upsertExercise(exercise = exercise)

    suspend fun deleteExercise(exercise: Exercise) = dao.deleteExercise(exercise = exercise)

    suspend fun getExercisesByName(): Flow<MutableMap<Int, Exercise>> = dao.getExercisesByName()

    suspend fun getExerciseWithTrainingPlanExercises() = dao.getExerciseWithTrainingPlanExercises()
}