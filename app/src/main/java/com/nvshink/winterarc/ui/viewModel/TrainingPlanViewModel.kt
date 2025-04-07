package com.nvshink.winterarc.ui.viewModel

import androidx.lifecycle.ViewModel
import com.nvshink.winterarc.data.model.TrainingPlan
import com.nvshink.winterarc.data.repository.TrainingPlanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TrainingPlanViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TrainingPlanUiState())
    val uiState: StateFlow<TrainingPlanUiState> = _uiState

//    init {
//        initializeUIState()
//    }
//
//    private fun initializeUIState() {
////        val trainingPlans: MutableMap<Int, TrainingPlan> =
////            TrainingPlanRepository().getTrainingPlans()
////        _uiState.value =
////            TrainingPlanUiState(
////                trainingPlanMap = trainingPlans
////            )
//    }

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
    val trainingPlanMap: MutableMap<Int, TrainingPlan> = mutableMapOf(),
    val currentTrainingPlan: TrainingPlan? = null,
    val isShowingList: Boolean = true
)