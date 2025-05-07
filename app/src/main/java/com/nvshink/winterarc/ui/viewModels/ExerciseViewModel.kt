package com.nvshink.winterarc.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.data.repository.ExerciseRepository
import com.nvshink.winterarc.ui.event.ExerciseEvent
import com.nvshink.winterarc.ui.states.ExerciseUiState
import com.nvshink.winterarc.ui.utils.SortTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.core.net.toUri

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
open class ExerciseViewModel @Inject constructor(
    private val repository: ExerciseRepository
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortTypes.NAME)

    private val _uiState = MutableStateFlow(_sortType
        .flatMapLatest { sortType ->
            flow {
                emit(ExerciseUiState.Loading())
                try {
                    val exercises: Flow<MutableMap<Int, Exercise>>
                    when (sortType) {
                        SortTypes.NAME -> exercises = repository.getExercisesByName()
                    }
                    emit(ExerciseUiState.Success(
                        exercisesMap = exercises.last()
                    ))
                } catch (_: Error) {
                    emit(ExerciseUiState.Error())
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ExerciseUiState.Loading()).value)


    val uiState = combine(_uiState, _sortType) { uiState, sortType ->
        when (uiState) {
            is ExerciseUiState.Loading -> uiState.copy(sortType = sortType)
            is ExerciseUiState.Success -> uiState.copy(sortType = sortType)
            is ExerciseUiState.Error -> uiState.copy(sortType = sortType)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ExerciseUiState.Loading())

    fun onEvent(event: ExerciseEvent) {
        when (event) {
            is ExerciseEvent.DeleteExercise -> {
                viewModelScope.launch {
                    repository.deleteExercise(event.exercise)
                    repository.deleteImagesList(event.exercise.images)
                }
            }

            is ExerciseEvent.SaveExercise -> {
                    val exercise: Exercise =
                        if (uiState.value.currentExercise == null || uiState.value.isAddingExercise) {
                            Exercise(
                                name = uiState.value.name,
                                images = repository.saveImagesList(
                                    context = event.context,
                                    uriList = uiState.value.images
                                ),
                                description = uiState.value.description
                            )
                        } else {
                            uiState.value.currentExercise!!.copy(
                                name = uiState.value.name,
                                images = uiState.value.images.map { imagesUriString ->
                                    if (!uiState.value.currentExercise!!.images.contains(
                                            imagesUriString
                                        )
                                    ) {
                                        return@map repository.saveImageByUri(
                                            context = event.context,
                                            imagesUriString.toUri()
                                        ).toString()
                                    } else {
                                        return@map imagesUriString
                                    }
                                },
                                description = uiState.value.description
                            )
                        }
                    viewModelScope.launch {
                        repository.upsertExercise(exercise)
                        if (uiState.value.currentExercise != null) {
                            uiState.value.currentExercise!!.images.forEach {
                                if (!uiState.value.images.contains(it)) repository.deleteImage(it.toUri())
                            }
                        }
                    }
            }

            is ExerciseEvent.UpdateCurrentExercise -> {
                _uiState.update { it as ExerciseUiState.Loading
                        it.copy(
                            currentExercise = event.exercise,
                        )
                }
            }

            is ExerciseEvent.SetName -> {
                _uiState.update { it as ExerciseUiState.Loading
                    it.copy(
                        name = event.name
                    )
                }
            }

            is ExerciseEvent.SetDescription -> {
                _uiState.update { it as ExerciseUiState.Loading
                    it.copy(
                        description = event.description
                    )
                }
            }

            is ExerciseEvent.SetImages -> {
                _uiState.update { it as ExerciseUiState.Loading
                    it.copy(
                        images = event.images
                    )
                }
            }

            ExerciseEvent.HideDialog -> {
                _uiState.update { it as ExerciseUiState.Loading
                    it.copy(
                        isShowingEditDialog = false
                    )
                }
            }

            is ExerciseEvent.ShowDialog -> {
                _uiState.update { it as ExerciseUiState.Loading
                    it.copy(
                        isShowingEditDialog = true,
                        isAddingExercise = event.isAdding
                    )
                }
            }

            ExerciseEvent.HideList -> {
                _uiState.update { it as ExerciseUiState.Loading
                    it.copy(
                        isShowingList = false
                    )
                }
            }

            ExerciseEvent.ShowList -> {
                _uiState.update { it as ExerciseUiState.Loading
                    it.copy(
                        isShowingList = true
                    )
                }
            }

            is ExerciseEvent.SetIsBigScreen -> {
                _uiState.update { it as ExerciseUiState.Loading
                    it.copy(
                        isBigScreen = event.isBigScreen
                    )
                }
            }

            is ExerciseEvent.SortExercises -> {
                _sortType.value = event.sortType
            }
        }
    }


}

