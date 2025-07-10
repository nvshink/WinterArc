package com.nvshink.winterarc.ui.viewModels.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nvshink.domain.resouce.Resource
import com.nvshink.domain.exercise.repository.ExerciseRepository
import com.nvshink.winterarc.ui.event.exercise.ExerciseListEvent
import com.nvshink.winterarc.ui.states.exercise.ExerciseListUiState
import com.nvshink.winterarc.ui.utils.SortTypes
import com.nvshink.winterarc.ui.utils.WinterArcContentType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
open class ExerciseListViewModel @Inject constructor(
    private val repository: ExerciseRepository
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortTypes.NAME_ASC)

    private val _isRefresh = MutableStateFlow(false)
    private val _contentType = MutableStateFlow(WinterArcContentType.LIST_ONLY)

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

    val listUIState = combine(
        _listUIState,
        _contentType,
        _exercises
    ) { listUIState, contentType, exercises ->
        when (exercises) {
            is Resource.Loading -> {
                _listUIState.update {
                    ExerciseListUiState.LoadingStateList(
                        isShowingList = it.isShowingList,
                        sortType = it.sortType,
                        contentType = contentType
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
                        sortType = listUIState.sortType,
                        contentType = contentType
                    )
                }
            }

            is Resource.Error -> {
                _listUIState.update {
                    ExerciseListUiState.ErrorStateList(
                        isShowingList = it.isShowingList,
                        sortType = it.sortType,
                        contentType = contentType
                    )
                }
            }
        }
        listUIState
    }.stateIn(
        viewModelScope,
        SharingStarted.Companion.WhileSubscribed(5000),
        ExerciseListUiState.LoadingStateList()
    )


    fun onListEvent(event: ExerciseListEvent) { //TODO(Solve the problem of code repetition)
        when (event) {
            ExerciseListEvent.HideList -> {
                _listUIState.update {
                    when (it) {
                        is ExerciseListUiState.SuccessStateList -> {
                            it.copy(
                                isShowingList = false
                            )
                        }

                        is ExerciseListUiState.LoadingStateList -> {
                            it.copy(
                                isShowingList = false
                            )
                        }

                        is ExerciseListUiState.ErrorStateList -> {
                            it.copy(
                                isShowingList = false
                            )
                        }
                    }
                }
            }

            ExerciseListEvent.ShowList -> {
                _listUIState.update {
                    when (it) {
                        is ExerciseListUiState.SuccessStateList -> {
                            it.copy(
                                isShowingList = true
                            )
                        }

                        is ExerciseListUiState.LoadingStateList -> {
                            it.copy(
                                isShowingList = true
                            )
                        }

                        is ExerciseListUiState.ErrorStateList -> {
                            it.copy(
                                isShowingList = true
                            )
                        }
                    }
                }
            }

            is ExerciseListEvent.SetContentType -> {
                _contentType.update {
                    event.contentType
                }
            }

            is ExerciseListEvent.SortExercises -> {
                _sortType.update {
                    event.sortType
                }
            }

            is ExerciseListEvent.UpdateCurrentExercise -> {
                _listUIState.update {
                    when (it) {
                        is ExerciseListUiState.SuccessStateList -> {
                            it.copy(
                                currentExercise = event.exercise,
                            )
                        }

                        else -> it
                    }
                }
            }
        }
    }
}