package com.example.winterarc.ui

import androidx.lifecycle.ViewModel
import com.example.winterarc.data.model.Exercise
import com.example.winterarc.data.repository.ExerciseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ExerciseViewModel : ViewModel() {
    private val exerciseRepository = ExerciseRepository()

    private val _uiState = MutableStateFlow(
        ExerciseUiState (
            exercisesList = exerciseRepository.getExercises(),
            currentExercise = exerciseRepository.getExercises().getOrElse(0) {
                null
            }
        )
    )
    val uiState: StateFlow<ExerciseUiState> = _uiState

    fun updateExerciseItemState(exercise: Exercise) {
        _uiState.update {
            it.copy(
                currentExercise = exercise,
                isShowingList = false
            )
        }
    }

    fun resetExercisesListItemState() {
        _uiState.update {
            it.copy(
                currentExercise = null,
                isShowingList = true
            )
        }
    }


}

data class ExerciseUiState(
    val exercisesList: List<Exercise> = emptyList(),
    val currentExercise: Exercise? = null,
    val isShowingList: Boolean = true
)