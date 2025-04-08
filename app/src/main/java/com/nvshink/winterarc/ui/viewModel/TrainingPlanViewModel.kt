package com.nvshink.winterarc.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nvshink.winterarc.data.model.TrainingPlan
import com.nvshink.winterarc.data.model.TrainingPlanExercise
import com.nvshink.winterarc.data.repository.TrainingPlanRepository
import com.nvshink.winterarc.ui.event.ExerciseEvent
import com.nvshink.winterarc.ui.event.TrainingPlanEvent
import com.nvshink.winterarc.ui.utils.SortTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TrainingPlanViewModel @Inject constructor(
    private val repository: TrainingPlanRepository
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortTypes.NAME)

    private val _trainingPlans = _sortType
        .flatMapLatest { sortType ->
            when (sortType) {
                SortTypes.NAME -> repository.getTrainingPlansByName()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), mutableMapOf())

    private val _uiState = MutableStateFlow(TrainingPlanUiState())

    val uiState = combine(_uiState, _sortType, _trainingPlans) { uiState, sortType, trainingPlans ->
        uiState.copy(
            trainingPlansMap = trainingPlans,
            sortType = sortType,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TrainingPlanUiState())

    fun onEvent(event: TrainingPlanEvent) {
        when(event) {
            is TrainingPlanEvent.DeleteTrainingPlan -> {
                viewModelScope.launch {
                    repository.deleteTrainingPlan(event.trainingPlan)
                }
            }
            is TrainingPlanEvent.SaveTrainingPlan -> {
                val trainingPlan: TrainingPlan = uiState.value.currentTrainingPlan ?: TrainingPlan(
                    name = uiState.value.name,
                    description = uiState.value.description
                )
                viewModelScope.launch {
                    repository.upsertTrainingPlan(trainingPlan)
                }
            }
            is TrainingPlanEvent.HideDialog -> TODO()
            is TrainingPlanEvent.HideList -> TODO()
            is TrainingPlanEvent.SetDescription -> TODO()
            is TrainingPlanEvent.SetImages -> TODO()
            is TrainingPlanEvent.SetName -> TODO()
            is TrainingPlanEvent.SetTrainingPlanExercises -> TODO()
            is TrainingPlanEvent.ShowDialog -> TODO()
            is TrainingPlanEvent.ShowList -> TODO()
            is TrainingPlanEvent.SortTrainingPlan -> TODO()
            is TrainingPlanEvent.UpdateCurrentTrainingPlan -> TODO()
        }
    }

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
    val trainingPlansMap: MutableMap<Int, TrainingPlan> = mutableMapOf(),
    val currentTrainingPlan: TrainingPlan? = null,
    val name: String = "",
    val description: String = "",
    val images: List<String> = emptyList(),
    val trainingPlanExercises: List<TrainingPlanExercise> = emptyList(),
    val isShowingList: Boolean = true,
    val isAddingDialog: Boolean = false,
    val sortType: SortTypes = SortTypes.NAME
)