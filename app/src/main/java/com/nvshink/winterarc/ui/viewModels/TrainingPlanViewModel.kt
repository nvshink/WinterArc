package com.nvshink.winterarc.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nvshink.winterarc.data.model.TrainingPlan
import com.nvshink.winterarc.data.repository.TrainingPlanRepository
import com.nvshink.winterarc.ui.event.TrainingPlanEvent
import com.nvshink.winterarc.ui.states.TrainingPlanUiState
import com.nvshink.winterarc.ui.utils.SortTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
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
        when (event) {
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
                    repository.createTrainingPlanWithExercises(trainingPlan, uiState.value.trainingPlanExercises)
                }
            }

            is TrainingPlanEvent.UpdateCurrentTrainingPlan -> {
                _uiState.update {
                    it.copy(
                        currentTrainingPlan = event.trainingPlan
                    )
                }
            }

            is TrainingPlanEvent.SetName -> {
                _uiState.update {
                    it.copy(
                        name = event.name
                    )
                }
            }

            is TrainingPlanEvent.SetDescription -> {
                _uiState.update {
                    it.copy(
                        description = event.description
                    )
                }
            }

            is TrainingPlanEvent.SetTrainingPlanExercises -> {
                _uiState.update {
                    it.copy(
                        trainingPlanExercises = event.trainingPlanExercises
                    )
                }
            }

            TrainingPlanEvent.HideDialog -> {
                _uiState.update {
                    it.copy(
                        isShowingEditDialog = false
                    )
                }
            }

            is TrainingPlanEvent.ShowDialog -> {
                _uiState.update {
                    it.copy(
                        isShowingEditDialog = true,
                        isAddingTrainingPlan = event.isAdding
                    )
                }
            }

            TrainingPlanEvent.HideList -> {
                _uiState.update {
                    it.copy(
                        isShowingList = false
                    )
                }
            }

            TrainingPlanEvent.ShowList -> {
                _uiState.update {
                    it.copy(
                        isShowingList = true
                    )
                }
            }

            is TrainingPlanEvent.SetIsBigScreen -> {
                _uiState.update {
                    it.copy(
                        isBigScreen = event.isBigScreen
                    )
                }
            }

            is TrainingPlanEvent.SortTrainingPlan -> {
                _sortType.value = event.sortType
            }
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

