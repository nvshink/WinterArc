package com.nvshink.domain.exercise.usecase

import com.nvshink.domain.exercise.model.ExerciseModel
import com.nvshink.domain.exercise.repository.ExerciseRepository

class SaveExerciseUseCase(private val exerciseRepository: ExerciseRepository, private val exerciseModel: ExerciseModel) {
    suspend fun execute () = exerciseRepository.upsertExercise(exerciseModel)
}