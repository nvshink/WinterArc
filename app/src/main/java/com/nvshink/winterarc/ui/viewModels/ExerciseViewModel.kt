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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.core.net.toUri
import com.nvshink.winterarc.data.utils.dataStatus
import com.nvshink.winterarc.ui.states.ExerciseUiState.*
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
open class ExerciseViewModel @Inject constructor(
    private val repository: ExerciseRepository
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortTypes.NAME_ASC)

    private val _isLoading = MutableStateFlow(dataStatus.LOADING)

    private val _exercises = _sortType
        .flatMapLatest { sortType ->
            _isLoading.update { dataStatus.LOADING }
            val exercises: Flow<List<Exercise>> = when (sortType) {
                SortTypes.NAME_ASC -> repository.getExercisesByNameASC()
                SortTypes.NAME_DESC -> repository.getExercisesByNameDESC()
            }
            _isLoading.update { dataStatus.SUCCESS }
            return@flatMapLatest exercises
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    private val _uiState = MutableStateFlow<ExerciseUiState>(LoadingState())

    val uiState = combine(
        _uiState,
        _exercises,
        _isLoading
    ) { state, exercises, isLoading ->
        val exercisesMap = exercises.associateBy { it.id }
        when (state) {
            is LoadingState -> {
                when (isLoading) {
                    dataStatus.LOADING -> state.copy(
                        sortType = _sortType.value
                    )

                    dataStatus.SUCCESS ->
                        SuccessState(
                            exercisesMap = exercisesMap,
                            currentExercise = state.currentExercise,
                            name = state.name,
                            description = state.description,
                            images = state.images,
                            isShowingList = state.isShowingList,
                            isAddingExercise = state.isAddingExercise,
                            isBigScreen = state.isBigScreen,
                            isShowingEditDialog = state.isShowingEditDialog,
                            sortType = _sortType.value
                        )

                    dataStatus.ERROR ->
                        ErrorState(
                            currentExercise = state.currentExercise,
                            name = state.name,
                            description = state.description,
                            images = state.images,
                            isShowingList = state.isShowingList,
                            isAddingExercise = state.isAddingExercise,
                            isBigScreen = state.isBigScreen,
                            isShowingEditDialog = state.isShowingEditDialog,
                            sortType = state.sortType
                        )
                }
            }

            is SuccessState -> {
                when (isLoading) {
                    dataStatus.LOADING ->
                        LoadingState(
                            currentExercise = state.currentExercise,
                            name = state.name,
                            description = state.description,
                            images = state.images,
                            isShowingList = state.isShowingList,
                            isAddingExercise = state.isAddingExercise,
                            isBigScreen = state.isBigScreen,
                            isShowingEditDialog = state.isShowingEditDialog,
                            sortType = state.sortType
                        )

                    dataStatus.SUCCESS -> state.copy(
                        exercisesMap = exercisesMap,
                        sortType = _sortType.value
                    )

                    dataStatus.ERROR ->
                        ErrorState(
                            currentExercise = state.currentExercise,
                            name = state.name,
                            description = state.description,
                            images = state.images,
                            isShowingList = state.isShowingList,
                            isAddingExercise = state.isAddingExercise,
                            isBigScreen = state.isBigScreen,
                            isShowingEditDialog = state.isShowingEditDialog,
                            sortType = state.sortType
                        )
                }
            }

            is ErrorState -> {
                when (isLoading) {
                    dataStatus.LOADING ->
                        LoadingState(
                            currentExercise = state.currentExercise,
                            name = state.name,
                            description = state.description,
                            images = state.images,
                            isShowingList = state.isShowingList,
                            isAddingExercise = state.isAddingExercise,
                            isBigScreen = state.isBigScreen,
                            isShowingEditDialog = state.isShowingEditDialog,
                            sortType = state.sortType
                        )


                    dataStatus.SUCCESS ->
                        SuccessState(
                            exercisesMap = exercisesMap,
                            currentExercise = state.currentExercise,
                            name = state.name,
                            description = state.description,
                            images = state.images,
                            isShowingList = state.isShowingList,
                            isAddingExercise = state.isAddingExercise,
                            isBigScreen = state.isBigScreen,
                            isShowingEditDialog = state.isShowingEditDialog,
                            sortType = _sortType.value
                        )

                    dataStatus.ERROR -> state.copy(
                        sortType = _sortType.value
                    )
                }
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LoadingState())


    fun onEvent(event: ExerciseEvent) {
        when (_uiState.value) {
            is SuccessState -> {
                val state = _uiState.value as SuccessState
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
                                    if (!uiState.value.images.contains(it)) repository.deleteImage(
                                        it.toUri()
                                    )
                                }
                            }
                        }
                    }

                    is ExerciseEvent.UpdateCurrentExercise -> {
                        _uiState.update {
                            state.copy(
                                currentExercise = event.exercise,
                            )
                        }
                    }

                    is ExerciseEvent.SetName -> {
                        _uiState.update {
                            state.copy(
                                name = event.name
                            )
                        }
                    }

                    is ExerciseEvent.SetDescription -> {
                        _uiState.update {
                            state.copy(
                                description = event.description
                            )
                        }
                    }

                    is ExerciseEvent.SetImages -> {
                        _uiState.update {
                            state.copy(
                                images = event.images
                            )
                        }
                    }

                    is ExerciseEvent.HideDialog -> {
                        _uiState.update {
                            state.copy(
                                isShowingEditDialog = false
                            )
                        }
                    }

                    is ExerciseEvent.ShowDialog -> {
                        _uiState.update {
                            state.copy(
                                isShowingEditDialog = true,
                                isAddingExercise = event.isAdding
                            )
                        }
                    }

                    is ExerciseEvent.HideList -> {
                        _uiState.update {
                            state.copy(
                                isShowingList = false
                            )
                        }
                    }

                    is ExerciseEvent.ShowList -> {
                        _uiState.update {
                            state.copy(
                                isShowingList = true
                            )
                        }
                    }

                    is ExerciseEvent.SetIsBigScreen -> {
                        _uiState.update {
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

            is LoadingState -> {
                val state = _uiState.value as LoadingState
                when (event) {
                    ExerciseEvent.HideDialog -> {
                        _uiState.update {
                            state.copy(
                                isShowingEditDialog = false
                            )
                        }
                    }

                    is ExerciseEvent.ShowDialog -> {
                        _uiState.update {
                            state.copy(
                                isShowingEditDialog = true,
                                isAddingExercise = event.isAdding
                            )
                        }
                    }

                    is ExerciseEvent.HideList -> {
                        _uiState.update {
                            state.copy(
                                isShowingList = false
                            )
                        }
                    }

                    is ExerciseEvent.ShowList -> {
                        _uiState.update {
                            state.copy(
                                isShowingList = true
                            )
                        }
                    }

                    is ExerciseEvent.SetIsBigScreen -> {
                        _uiState.update {
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

            is ErrorState -> {
                val state = _uiState.value as ErrorState
                when (event) {

                    is ExerciseEvent.HideDialog -> {
                        _uiState.update {
                            state.copy(
                                isShowingEditDialog = false
                            )
                        }
                    }

                    is ExerciseEvent.ShowDialog -> {
                        _uiState.update {
                            state.copy(
                                isShowingEditDialog = true,
                                isAddingExercise = event.isAdding
                            )
                        }
                    }

                    is ExerciseEvent.HideList -> {
                        _uiState.update {
                            state.copy(
                                isShowingList = false
                            )
                        }
                    }

                    is ExerciseEvent.ShowList -> {
                        _uiState.update {
                            state.copy(
                                isShowingList = true
                            )
                        }
                    }

                    is ExerciseEvent.SetIsBigScreen -> {
                        _uiState.update {
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

