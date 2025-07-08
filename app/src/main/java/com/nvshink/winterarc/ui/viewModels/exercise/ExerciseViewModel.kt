package com.nvshink.winterarc.ui.viewModels.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nvshink.domain.resouce.Resource
import com.nvshink.domain.exercise.model.ExerciseModel
import com.nvshink.domain.exercise.repository.ExerciseRepository
import com.nvshink.winterarc.ui.event.ExerciseEvent
import com.nvshink.winterarc.ui.states.exercise.ExerciseDetailUIState
import com.nvshink.winterarc.ui.states.exercise.ExerciseListUiState
import com.nvshink.winterarc.ui.utils.SortTypes
import com.nvshink.winterarc.ui.utils.dataStatus
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
open class ExerciseViewModel @Inject constructor(
    private val repository: ExerciseRepository
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortTypes.NAME_ASC)

    private val _isRefresh = MutableStateFlow(false)

    private val _exercises = combine(
        _sortType,
        _isRefresh
    ) { sortType, _ ->
        _isRefresh.update { false }
        when (sortType) {
            SortTypes.NAME_ASC -> repository.getExercisesByNameASC()
            SortTypes.NAME_DESC -> repository.getExercisesByNameDESC()
        }
    }.flatMapLatest { it }
        .stateIn(
            viewModelScope,
            SharingStarted.Companion.WhileSubscribed(5000),
            Resource.Loading
        )

    private val _listUIState =
        MutableStateFlow<ExerciseListUiState>(ExerciseListUiState.LoadingStateList())

    val listUIState: StateFlow<ExerciseListUiState> = combine(
        _listUIState,
        _exercises
    ) { listUIState, exercises ->
        when (exercises) {
            is Resource.Loading -> {
                _listUIState.update {
                    ExerciseListUiState.LoadingStateList(
                        isShowingList = it.isShowingList,
                        sortType = it.sortType
                    )
                }
            }

            is Resource.Success -> {
                val exercisesMap = exercises.data.associateBy { it.id }
                _listUIState.update {
                    ExerciseListUiState.SuccessStateList(
                        exercisesMap = exercisesMap,
                        currentExercise = if (listUIState is ExerciseListUiState.SuccessStateList) listUIState.currentExercise else null,
                        isShowingList = listUIState.isShowingList,
                        sortType = listUIState.sortType
                    )
                }
            }

            is Resource.Error -> {
                _listUIState.update {
                    ExerciseListUiState.ErrorStateList(
                        isShowingList = it.isShowingList,
                        sortType = it.sortType
                    )
                }
            }
        }
        listUIState
    }.stateIn(
        viewModelScope, SharingStarted.Companion.WhileSubscribed(5000),
        ExerciseListUiState.LoadingStateList()
    )

    private val _exercise = _listUIState.flatMapLatest {
        if(it is ExerciseListUiState.SuccessStateList) { //TODO
            it.currentExercise
        }
    }

    private val _detailUIState =
        MutableStateFlow<ExerciseDetailUIState>(ExerciseDetailUIState.LoadingState())

    val detailUIState = combine(
        _detailUIState
    ) { detailUIState ->

    }

    fun onEvent(event: ExerciseEvent) {
        when (_listUIState.value) {
            is ExerciseListUiState.SuccessStateList -> {
                val state = _listUIState.value as ExerciseListUiState.SuccessStateList
                when (event) {
                    is ExerciseEvent.DeleteExercise -> {
                        viewModelScope.launch {
                            repository.deleteExercise(event.exercise)
                        }
                    }

                    is ExerciseEvent.SaveExercise -> {
                        viewModelScope.launch {
                            repository.upsertExercise(event.exercise)
                        }
                    }

                    is ExerciseEvent.UpdateCurrentExercise -> {
                        _listUIState.update {
                            state.copy(
                                currentExercise = event.exercise,
                            )
                        }
                    }

                    is ExerciseEvent.SetName -> {
                        _listUIState.update {
                            state.copy(
                                name = event.name
                            )
                        }
                    }

                    is ExerciseEvent.SetDescription -> {
                        _listUIState.update {
                            state.copy(
                                description = event.description
                            )
                        }
                    }

                    is ExerciseEvent.SetImages -> {
                        _listUIState.update {
                            state.copy(
                                images = event.images
                            )
                        }
                    }

                    is ExerciseEvent.HideList -> {
                        _listUIState.update {
                            state.copy(
                                isShowingList = false
                            )
                        }
                    }

                    is ExerciseEvent.ShowList -> {
                        _listUIState.update {
                            state.copy(
                                isShowingList = true
                            )
                        }
                    }

                    is ExerciseEvent.SetIsBigScreen -> {
                        _listUIState.update {
                            state.copy(
                                isBigScreen = event.isBigScreen
                            )
                        }
                    }

                    is ExerciseEvent.SortExercises -> {
                        _sortType.update {
                            event.sortType
                        }
                    }
                }
            }

            is ExerciseListUiState.LoadingStateList -> {
                val state = _listUIState.value as ExerciseListUiState.LoadingStateList
                when (event) {

                    is ExerciseEvent.HideList -> {
                        _listUIState.update {
                            state.copy(
                                isShowingList = false
                            )
                        }
                    }

                    is ExerciseEvent.ShowList -> {
                        _listUIState.update {
                            state.copy(
                                isShowingList = true
                            )
                        }
                    }

                    is ExerciseEvent.SetIsBigScreen -> {
                        _listUIState.update {
                            state.copy(
                                isBigScreen = event.isBigScreen
                            )
                        }
                    }

                    is ExerciseEvent.SortExercises -> {
                        _sortType.value = event.sortType
                    }

                    else -> {}
                }
            }

            is ExerciseListUiState.ErrorStateList -> {
                val state = _listUIState.value as ExerciseListUiState.ErrorStateList
                when (event) {

                    is ExerciseEvent.HideList -> {
                        _listUIState.update {
                            state.copy(
                                isShowingList = false
                            )
                        }
                    }

                    is ExerciseEvent.ShowList -> {
                        _listUIState.update {
                            state.copy(
                                isShowingList = true
                            )
                        }
                    }

                    is ExerciseEvent.SetIsBigScreen -> {
                        _listUIState.update {
                            state.copy(
                                isBigScreen = event.isBigScreen
                            )
                        }
                    }

                    is ExerciseEvent.SortExercises -> {
                        _sortType.value = event.sortType
                    }

                    else -> {}
                }
            }
        }
    }
}