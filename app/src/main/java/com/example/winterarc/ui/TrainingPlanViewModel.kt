package com.example.winterarc.ui

import androidx.lifecycle.ViewModel
import com.example.winterarc.data.model.Exercise
import com.example.winterarc.data.model.TrainingPlan
import com.example.winterarc.data.repository.TrainingPlanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TrainingPlanViewModel : ViewModel() {
    private val trainingPlanRepository = TrainingPlanRepository()

    private val _uiState = MutableStateFlow(
        TrainingPlanUiState (
            trainingPlanList = trainingPlanRepository.getTrainingPlans(),
            currentTrainingPlan = trainingPlanRepository.getTrainingPlans().getOrElse(0) {
                null
            }
        )
    )
    val uiState: StateFlow<TrainingPlanUiState> = _uiState


    fun updateTrainingPlanItemState(trainingPlan: TrainingPlan) {
        _uiState.update {
            it.copy(
                currentTrainingPlan = trainingPlan,
                isShowingList = false
            )
        }
    }

    fun resetTrainingPlansListState() {
        _uiState.update {
            it.copy(
                currentTrainingPlan = null,
                isShowingList = true
            )
        }
    }



}

data class TrainingPlanUiState(
    val trainingPlanList: List<TrainingPlan> = emptyList(),
    val currentTrainingPlan: TrainingPlan? = null,
    val isShowingList: Boolean = true
)