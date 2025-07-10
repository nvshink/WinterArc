package com.nvshink.winterarc.ui.viewModels.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nvshink.domain.exercise.repository.ExerciseRepository
import com.nvshink.domain.resouce.Resource
import com.nvshink.winterarc.ui.event.exercise.ExerciseDetailEvent
import com.nvshink.winterarc.ui.states.exercise.ExerciseDetailUIState
import com.nvshink.winterarc.ui.states.exercise.ExerciseListUiState
import com.nvshink.winterarc.ui.utils.WinterArcContentType
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
open class ExerciseDetailViewModel @Inject constructor(
    private val repository: ExerciseRepository
) : ViewModel() {

    private val _contentType = MutableStateFlow(WinterArcContentType.LIST_ONLY)

    private val _exerciseId = MutableStateFlow(0L)
    private val _exercise = _exerciseId
        .flatMapLatest { id ->
            repository.getExerciseById(id)
        }.stateIn(
            viewModelScope,
            SharingStarted.Companion.WhileSubscribed(5000),
            Resource.Loading
        )

    private val _exerciseErrorMessage = MutableStateFlow("")

    private val _detailUIState =
        MutableStateFlow<ExerciseDetailUIState>(ExerciseDetailUIState.LoadingState())

    val detailUIState = combine(
        _detailUIState,
        _exercise,
        _exerciseErrorMessage,
        _contentType
    ) { detailUIState, exercise, exerciseErrorMessage, contentType ->
        when (exercise) {
            is Resource.Loading -> {
                _detailUIState.update {
                    ExerciseDetailUIState.LoadingState(
                        exercise = null,
                        contentType = contentType
                    )
                }
            }

            is Resource.Success -> {
                when (detailUIState) {
                    is ExerciseDetailUIState.ViewState -> {
                        _detailUIState.update {
                            ExerciseDetailUIState.ViewState(
                                exercise = exercise.data,
                                contentType = contentType
                            )
                        }
                    }

                    is ExerciseDetailUIState.AddState -> {
                        _detailUIState.update {
                            ExerciseDetailUIState.AddState(
                                exercise = exercise.data,
                                contentType = contentType,
                                name = "",
                                description = "",
                                imageLinks = emptyList()
                            )
                        }
                    }

                    is ExerciseDetailUIState.EditState -> {
                        _detailUIState.update {
                            ExerciseDetailUIState.EditState(
                                exercise = exercise.data,
                                contentType = contentType,
                                name = exercise.data.name,
                                description = exercise.data.description,
                                imageLinks = exercise.data.imageLinks
                            )
                        }
                    }
                }
            }

            is Resource.Error -> {
                when (detailUIState) {
                    is ExerciseDetailUIState.ErrorState -> {
                        _detailUIState.update {
                            ExerciseDetailUIState.ErrorState(
                                exercise = null,
                                contentType = contentType,
                                errorMessage = exerciseErrorMessage
                            )
                        }
                    }
                }
            }
        }
        detailUIState
    }.stateIn(
        viewModelScope,
        SharingStarted.Companion.WhileSubscribed(5000),
        ExerciseDetailUIState.LoadingState()
    )

    fun onDetailEvent(event: ExerciseDetailEvent) { //TODO(Solve the problem of code repetition)
        when (event) {
            is ExerciseDetailEvent.SetExercise -> {
                _exerciseId.update {
                    event.id
                }
            }

            is ExerciseDetailEvent.DeleteExercise -> {
                viewModelScope.launch {
                    repository.deleteExercise(event.exercise)
                }
            }

            is ExerciseDetailEvent.SaveExercise -> {
                viewModelScope.launch {
                    repository.upsertExercise(event.exercise)
                }
            }

            is ExerciseDetailEvent.SetName -> {
                _detailUIState.update {
                    when (it) {
                        is ExerciseDetailUIState.EditState -> {
                            it.copy(
                                name = event.name
                            )
                        }

                        is ExerciseDetailUIState.AddState -> {
                            it.copy(
                                name = event.name
                            )
                        }

                        else -> it
                    }
                }
            }

            is ExerciseDetailEvent.SetDescription -> {
                _detailUIState.update {
                    when (it) {
                        is ExerciseDetailUIState.EditState -> {
                            it.copy(
                                description = event.description
                            )
                        }

                        is ExerciseDetailUIState.AddState -> {
                            it.copy(
                                description = event.description
                            )
                        }

                        else -> it
                    }
                }
            }

            is ExerciseDetailEvent.SetImages -> {
                _detailUIState.update {
                    when (it) {
                        is ExerciseDetailUIState.EditState -> {
                            it.copy(
                                imageLinks = event.images
                            )
                        }

                        is ExerciseDetailUIState.AddState -> {
                            it.copy(
                                imageLinks = event.images
                            )
                        }

                        else -> it
                    }
                }
            }

            is ExerciseDetailEvent.SetContentType -> {
                _contentType.update {
                    event.contentType
                }
            }

            ExerciseDetailEvent.AddExercise -> {
                _detailUIState.update { ExerciseDetailUIState.AddState() }
            }

            ExerciseDetailEvent.EditExercise -> {
                _detailUIState.update { ExerciseDetailUIState.EditState() }

            }

            ExerciseDetailEvent.ViewExercise -> {
                _detailUIState.update { ExerciseDetailUIState.ViewState() }
            }
        }
    }
}