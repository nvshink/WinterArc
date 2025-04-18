package com.nvshink.winterarc.ui.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.data.repository.ExerciseRepository
import com.nvshink.winterarc.ui.event.ExerciseEvent
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
open class ExerciseViewModel @Inject constructor(
    private val repository: ExerciseRepository
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortTypes.NAME)

    private val _exercises = _sortType
        .flatMapLatest { sortType ->
            when (sortType) {
                SortTypes.NAME -> repository.getExercisesByName()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), mutableMapOf())

    private val _uiState = MutableStateFlow(ExerciseUiState())

    val uiState = combine(_uiState, _sortType, _exercises) { uiState, sortType, exercises ->
        uiState.copy(
            exercisesMap = exercises,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ExerciseUiState())

    fun resetExercisesListItemState() {
        _uiState.update {
            it.copy(
                currentExercise = null,
                isShowingList = true
            )
        }
    }

    fun onEvent(event: ExerciseEvent) {
        when (event) {
            is ExerciseEvent.DeleteExercise -> {
                viewModelScope.launch {
                    repository.deleteExercise(event.exercise)
                    repository.deleteExerciseImagesListFromLocalStorage(event.exercise.images)
                }
            }

            is ExerciseEvent.SaveExercise -> {
                val exercise: Exercise =
                    if (uiState.value.currentExercise == null || uiState.value.isAddingExercise) {
                        Exercise(
                            name = uiState.value.name,
                            images = repository.saveExerciseImagesListToLocalStorageAndGetList(
                                context = event.context,
                                uriList = uiState.value.images
                            ),
                            description = uiState.value.description
                        )
                    } else {
                        uiState.value.currentExercise!!.copy(
                            name = uiState.value.name,
                            images = uiState.value.images.map { imagesUriString ->
                                if (!uiState.value.currentExercise!!.images.contains(imagesUriString)) {
                                    return@map repository.saveExerciseImageToLocalStorageAndGetUri(
                                        context = event.context,
                                        Uri.parse(imagesUriString)
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
                            if (!uiState.value.images.contains(it)) repository.deleteExerciseImageFromLocalStorage(Uri.parse(it))
                        }
                    }
                }
            }

            is ExerciseEvent.UpdateCurrentExercise -> {
                _uiState.update {
                    it.copy(
                        currentExercise = event.exercise,
                    )
                }
            }

            is ExerciseEvent.SetName -> {
                _uiState.update {
                    it.copy(
                        name = event.name
                    )
                }
            }

            is ExerciseEvent.SetDescription -> {
                _uiState.update {
                    it.copy(
                        description = event.description
                    )
                }
            }

            is ExerciseEvent.SetImages -> {
                _uiState.update {
                    it.copy(
                        images = event.images
                    )
                }
            }

            ExerciseEvent.HideDialog -> {
                _uiState.update {
                    it.copy(
                        isShowingEditDialog = false
                    )
                }
            }

            is ExerciseEvent.ShowDialog -> {
                _uiState.update {
                    it.copy(
                        isShowingEditDialog = true,
                        isAddingExercise = event.isAdding
                    )
                }
            }

            ExerciseEvent.HideList -> {
                _uiState.update {
                    it.copy(
                        isShowingList = false
                    )
                }
            }

            ExerciseEvent.ShowList -> {
                _uiState.update {
                    it.copy(
                        isShowingList = true
                    )
                }
            }

            is ExerciseEvent.SetIsBigScreen -> {
                _uiState.update {
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

data class ExerciseUiState(
    val exercisesMap: MutableMap<Int, Exercise> = mutableMapOf(),
    val currentExercise: Exercise? = null,
    val name: String = "",
    val description: String = "",
    val images: List<String> = emptyList(),
    val isShowingList: Boolean = true,
    val isAddingExercise: Boolean = true,
    val isBigScreen: Boolean = false,
    val isShowingEditDialog: Boolean = false,
    val sortType: SortTypes = SortTypes.NAME
)