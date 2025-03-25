package com.example.winterarc.ui

import androidx.lifecycle.ViewModel
import com.example.winterarc.data.model.TrainingPlan
import com.example.winterarc.data.repository.TrainingPlanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TrainingPlanViewModel : ViewModel() {
    private val trainingPlanRepository = TrainingPlanRepository()

    private val _uiState = MutableStateFlow(
        TrainingPlanUiState (
            trainingPlanList = trainingPlanRepository.getTrainingPlans(),
            currentTrainingPlan = trainingPlanRepository.getTrainingPlans().getOrElse(0) {
                trainingPlanRepository.getTrainingPlans()[0]
            }
        )
    )
    val uiState: StateFlow<TrainingPlanUiState> = _uiState


}

data class TrainingPlanUiState(
    val trainingPlanList: List<TrainingPlan> = emptyList(),
    val currentTrainingPlan: TrainingPlan = trainingPlanList[0]
)