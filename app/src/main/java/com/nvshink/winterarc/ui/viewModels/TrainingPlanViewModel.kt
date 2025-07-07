package com.nvshink.winterarc.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nvshink.domain.trainingplan.model.TrainingPlanModel
import com.nvshink.domain.trainingplan.repository.TrainingPlanRepository
import com.nvshink.winterarc.ui.utils.dataStatus
import com.nvshink.winterarc.ui.event.TrainingPlanEvent
import com.nvshink.winterarc.ui.states.TrainingPlanUiState.ErrorState
import com.nvshink.winterarc.ui.states.TrainingPlanUiState.LoadingState
import com.nvshink.winterarc.ui.states.TrainingPlanUiState.SuccessState
import com.nvshink.winterarc.ui.states.TrainingPlanUiState
import com.nvshink.winterarc.ui.utils.SortTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
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

    private val _sortType = MutableStateFlow(SortTypes.NAME_ASC)

    private val _isLoading = MutableStateFlow(dataStatus.LOADING)

    private val _trainingPlans = _sortType
        .flatMapLatest { sortType ->
            _isLoading.update { dataStatus.LOADING }
            val trainingPlans: Flow<List<TrainingPlanModel>> = when (sortType) {
                SortTypes.NAME_ASC -> repository.getTrainingPlansByNameASC()
                SortTypes.NAME_DESC -> repository.getTrainingPlansByNameDESC()
            }
            _isLoading.update { dataStatus.SUCCESS }
            trainingPlans
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    private val _uiState = MutableStateFlow<TrainingPlanUiState>(LoadingState())

    val uiState = combine(
        _uiState,
        _sortType,
        _trainingPlans,
        _isLoading
    ) { uiState, sortType, trainingPlans, isLoading ->
        val trainingPlansMap = trainingPlans.associateBy { it.id }
        when (uiState) {
            is LoadingState -> {
                when (isLoading) {
                    dataStatus.LOADING -> uiState.copy(
                        sortType = _sortType.value
                    )

                    dataStatus.SUCCESS -> {
                        _uiState.update {
                            SuccessState(
                                trainingPlansMap = trainingPlansMap,
                                currentTrainingPlan = uiState.currentTrainingPlan,
                                name = uiState.name,
                                description = uiState.description,
//                                trainingPlanExercises = state.trainingPlanExercises,
                                isShowingList = uiState.isShowingList,
                                isAddingTrainingPlan = uiState.isAddingTrainingPlan,
                                isBigScreen = uiState.isBigScreen,
                                isShowingEditDialog = uiState.isShowingEditDialog,
                                sortType = _sortType.value
                            )
                        }
                        uiState.copy(
                            sortType = _sortType.value
                        )
                    }

                    dataStatus.ERROR -> {
                        _uiState.update{
                            ErrorState(
                                currentTrainingPlan = uiState.currentTrainingPlan,
                                name = uiState.name,
                                description = uiState.description,
//                                trainingPlanExercises = state.trainingPlanExercises,
                                isShowingList = uiState.isShowingList,
                                isAddingTrainingPlan = uiState.isAddingTrainingPlan,
                                isBigScreen = uiState.isBigScreen,
                                isShowingEditDialog = uiState.isShowingEditDialog,
                                sortType = _sortType.value
                            )
                        }
                        uiState.copy(
                            sortType = _sortType.value
                        )
                    }
                }
            }

            is SuccessState -> {
                when (isLoading) {
                    dataStatus.LOADING -> {
                        _uiState.update{
                            LoadingState(
                                currentTrainingPlan = uiState.currentTrainingPlan,
                                name = uiState.name,
                                description = uiState.description,
//                                trainingPlanExercises = state.trainingPlanExercises,
                                isShowingList = uiState.isShowingList,
                                isAddingTrainingPlan = uiState.isAddingTrainingPlan,
                                isBigScreen = uiState.isBigScreen,
                                isShowingEditDialog = uiState.isShowingEditDialog,
                                sortType = _sortType.value
                            )
                        }
                        uiState.copy(
                            trainingPlansMap = trainingPlansMap,
                            sortType = _sortType.value
                        )
                    }

                    dataStatus.SUCCESS -> uiState.copy(
                        trainingPlansMap = trainingPlansMap,
                        sortType = _sortType.value
                    )

                    dataStatus.ERROR -> {
                        _uiState.update{
                            ErrorState(
                                currentTrainingPlan = uiState.currentTrainingPlan,
                                name = uiState.name,
                                description = uiState.description,
//                                trainingPlanExercises = state.trainingPlanExercises,
                                isShowingList = uiState.isShowingList,
                                isAddingTrainingPlan = uiState.isAddingTrainingPlan,
                                isBigScreen = uiState.isBigScreen,
                                isShowingEditDialog = uiState.isShowingEditDialog,
                                sortType = _sortType.value
                            )
                        }
                        uiState.copy(
                            trainingPlansMap = trainingPlansMap,
                            sortType = _sortType.value
                        )
                    }
                }
            }

            is ErrorState -> {
                when (isLoading) {
                    dataStatus.LOADING ->
                        LoadingState(
                            currentTrainingPlan = uiState.currentTrainingPlan,
                            name = uiState.name,
                            description = uiState.description,
//                            trainingPlanExercises = state.trainingPlanExercises,
                            isShowingList = uiState.isShowingList,
                            isAddingTrainingPlan = uiState.isAddingTrainingPlan,
                            isBigScreen = uiState.isBigScreen,
                            isShowingEditDialog = uiState.isShowingEditDialog,
                            sortType = _sortType.value
                        )


                    dataStatus.SUCCESS ->
                        SuccessState(
                            currentTrainingPlan = uiState.currentTrainingPlan,
                            name = uiState.name,
                            description = uiState.description,
//                            trainingPlanExercises = state.trainingPlanExercises,
                            isShowingList = uiState.isShowingList,
                            isAddingTrainingPlan = uiState.isAddingTrainingPlan,
                            isBigScreen = uiState.isBigScreen,
                            isShowingEditDialog = uiState.isShowingEditDialog,
                            sortType = _sortType.value
                        )

                    dataStatus.ERROR -> uiState.copy(
                        sortType = _sortType.value
                    )
                }
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LoadingState())

    fun onEvent(event: TrainingPlanEvent) {
        when (_uiState.value) {
            is SuccessState -> {
                val state = _uiState.value as SuccessState
                when (event) {
                    is TrainingPlanEvent.DeleteTrainingPlan -> {
                        viewModelScope.launch {
                            repository.deleteTrainingPlan(event.trainingPlan)
                        }
                    }

                    is TrainingPlanEvent.SaveTrainingPlan -> {
                        val trainingPlan: TrainingPlanModel =
                            uiState.value.currentTrainingPlan ?: TrainingPlanModel(
                                name = uiState.value.name,
                                description = uiState.value.description
                            )
                        viewModelScope.launch {
//                            repository.createTrainingPlanWithExercises(
//                                trainingPlan,
//                                uiState.value.trainingPlanExercises
//                            )
                        }
                    }

                    is TrainingPlanEvent.UpdateCurrentTrainingPlan -> {
                        _uiState.update {
                            state.copy(
                                currentTrainingPlan = event.trainingPlan
                            )
                        }
                    }

                    is TrainingPlanEvent.SetName -> {
                        _uiState.update {
                            state.copy(
                                name = event.name
                            )
                        }
                    }

                    is TrainingPlanEvent.SetDescription -> {
                        _uiState.update {
                            state.copy(
                                description = event.description
                            )
                        }
                    }

                    TrainingPlanEvent.HideDialog -> {
                        _uiState.update {
                            state.copy(
                                isShowingEditDialog = false
                            )
                        }
                    }

                    is TrainingPlanEvent.ShowDialog -> {
                        _uiState.update {
                            state.copy(
                                isShowingEditDialog = true,
                                isAddingTrainingPlan = event.isAdding
                            )
                        }
                    }

                    TrainingPlanEvent.HideList -> {
                        _uiState.update {
                            state.copy(
                                isShowingList = false
                            )
                        }
                    }

                    TrainingPlanEvent.ShowList -> {
                        _uiState.update {
                            state.copy(
                                isShowingList = true
                            )
                        }
                    }

                    TrainingPlanEvent.HideExerciseSelector -> {
                        _uiState.update {
                            state.copy(
                                isShowingExerciseSelector = false
                            )
                        }
                    }

                    TrainingPlanEvent.ShowExerciseSelector -> {
                        _uiState.update {
                            state.copy(
                                isShowingExerciseSelector = true
                            )
                        }
                    }

                    is TrainingPlanEvent.SetIsBigScreen -> {
                        _uiState.update {
                            state.copy(
                                isBigScreen = event.isBigScreen
                            )
                        }
                    }

                    is TrainingPlanEvent.SortTrainingPlan -> {
                        _sortType.value = event.sortType
                    }

                }
            }

            is LoadingState -> {
                val state = _uiState.value as LoadingState
                when (event) {
                    is TrainingPlanEvent.UpdateCurrentTrainingPlan -> {
                        _uiState.update {
                            state.copy(
                                currentTrainingPlan = event.trainingPlan
                            )
                        }
                    }

                    is TrainingPlanEvent.SetName -> {
                        _uiState.update {
                            state.copy(
                                name = event.name
                            )
                        }
                    }

                    is TrainingPlanEvent.SetDescription -> {
                        _uiState.update {
                            state.copy(
                                description = event.description
                            )
                        }
                    }

                    TrainingPlanEvent.HideDialog -> {
                        _uiState.update {
                            state.copy(
                                isShowingEditDialog = false
                            )
                        }
                    }

                    is TrainingPlanEvent.ShowDialog -> {
                        _uiState.update {
                            state.copy(
                                isShowingEditDialog = true,
                                isAddingTrainingPlan = event.isAdding
                            )
                        }
                    }

                    TrainingPlanEvent.HideList -> {
                        _uiState.update {
                            state.copy(
                                isShowingList = false
                            )
                        }
                    }

                    TrainingPlanEvent.ShowList -> {
                        _uiState.update {
                            state.copy(
                                isShowingList = true
                            )
                        }
                    }

                    is TrainingPlanEvent.SetIsBigScreen -> {
                        _uiState.update {
                            state.copy(
                                isBigScreen = event.isBigScreen
                            )
                        }
                    }

                    is TrainingPlanEvent.SortTrainingPlan -> {
                        _sortType.value = event.sortType
                    }

                    else -> {}
                }
            }

            is ErrorState -> {
                val state = _uiState.value as ErrorState
                when (event) {
                    is TrainingPlanEvent.UpdateCurrentTrainingPlan -> {
                        _uiState.update {
                            state.copy(
                                currentTrainingPlan = event.trainingPlan
                            )
                        }
                    }

                    is TrainingPlanEvent.SetName -> {
                        _uiState.update {
                            state.copy(
                                name = event.name
                            )
                        }
                    }

                    is TrainingPlanEvent.SetDescription -> {
                        _uiState.update {
                            state.copy(
                                description = event.description
                            )
                        }
                    }

                    TrainingPlanEvent.HideDialog -> {
                        _uiState.update {
                            state.copy(
                                isShowingEditDialog = false
                            )
                        }
                    }

                    is TrainingPlanEvent.ShowDialog -> {
                        _uiState.update {
                            state.copy(
                                isShowingEditDialog = true,
                                isAddingTrainingPlan = event.isAdding
                            )
                        }
                    }

                    TrainingPlanEvent.HideList -> {
                        _uiState.update {
                            state.copy(
                                isShowingList = false
                            )
                        }
                    }

                    TrainingPlanEvent.ShowList -> {
                        _uiState.update {
                            state.copy(
                                isShowingList = true
                            )
                        }
                    }

                    is TrainingPlanEvent.SetIsBigScreen -> {
                        _uiState.update {
                            state.copy(
                                isBigScreen = event.isBigScreen
                            )
                        }
                    }

                    is TrainingPlanEvent.SortTrainingPlan -> {
                        _sortType.value = event.sortType
                    }

                    else -> {}
                }
            }

        }


    }

//    fun updateTrainingPlanItemState(trainingPlan: TrainingPlan) {
//        _uiState.update {
//            it.copy(
//                currentTrainingPlan = trainingPlan,
//                isShowingList = false
//            )
//        }
//    }
//
//    fun resetTrainingPlansListState() {
//        _uiState.update {
//            it.copy(
//                currentTrainingPlan = null,
//                isShowingList = true
//            )
//        }
//    }


}

