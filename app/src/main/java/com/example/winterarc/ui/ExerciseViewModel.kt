package com.example.winterarc.ui

import androidx.lifecycle.ViewModel
import com.example.winterarc.data.model.Exercise
import com.example.winterarc.data.repository.ExerciseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ExerciseViewModel : ViewModel() {
    private val exerciseRepository = ExerciseRepository()

    private val _uiState = MutableStateFlow(
        ExerciseUiState (
            exercisesList = exerciseRepository.getExercises(),
            currentExercise = exerciseRepository.getExercises().getOrElse(0) {
                exerciseRepository.getExercises()[0]
            }
        )
    )
    val uiState: StateFlow<ExerciseUiState> = _uiState


}

data class ExerciseUiState(
    val exercisesList: List<Exercise> = emptyList(),
    val currentExercise: Exercise = exercisesList[0]
)