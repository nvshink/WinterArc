package com.nvshink.winterarc.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.data.local.trainingplanexercise.entity.TrainingPlanExercise
import com.nvshink.winterarc.data.local.trainingplanexercise.entity.TrainingPlanExercise.TrainingPlanExerciseParams
import com.nvshink.winterarc.data.local.exercise.repository.ExerciseRepository
import com.nvshink.winterarc.data.local.trainingplan.repository.TrainingPlanRepository
import com.nvshink.winterarc.domain.utils.dataStatus
import com.nvshink.winterarc.ui.event.TrainingPlanExercisesEvent
import com.nvshink.winterarc.ui.states.TrainingPlanExerciseUiState
import com.nvshink.winterarc.ui.states.TrainingPlanExerciseUiState.SuccessState
import com.nvshink.winterarc.ui.states.TrainingPlanExerciseUiState.LoadingState
import com.nvshink.winterarc.ui.states.TrainingPlanExerciseUiState.ErrorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class TrainingPlanExerciseViewModel @Inject constructor(
    private val trainingPlanRepository: TrainingPlanRepository,
    private val exerciseRepository: ExerciseRepository
) : ViewModel() {
    private val _trainingPlanId = MutableStateFlow<Long?>(null)

    private val _isLoading = MutableStateFlow(dataStatus.LOADING)

    private val _trainingPlanExercises = _trainingPlanId
        .flatMapLatest { trainingPlanId ->
            _isLoading.update { dataStatus.LOADING }
            val trainingPlansExercises: Flow<List<TrainingPlanExercise>> =
                trainingPlanRepository.getTrainingPlanExercisesByTrainingPlanId(trainingPlanId ?: 0)
            _isLoading.update { dataStatus.SUCCESS }
            return@flatMapLatest trainingPlansExercises
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    private val pairExerciseAndParams =
        if (_trainingPlanId.value == null) {
            emptyList()
        } else {
            _trainingPlanExercises.value.map {
                Pair(
                    exerciseRepository.getExercisesById(it.exerciseIdForeignKey).last(),
                    TrainingPlanExerciseParams(it.duration, it.isInSets)
                )
            }
        }


    private val _uiState = MutableStateFlow<TrainingPlanExerciseUiState>(LoadingState())

    val uiState = combine(
        _uiState,
        _isLoading,
        _trainingPlanExercises
    ) { uiState, isLoading, trainingPlanExercises ->
        when (uiState) {
            is LoadingState -> {
                when (isLoading) {
                    dataStatus.LOADING -> uiState.copy(
                        trainingPlanExercises = trainingPlanExercises,
                        pairExerciseAndParams = pairExerciseAndParams

                    )

                    dataStatus.SUCCESS -> {
                        _uiState.update {
                            SuccessState(
                                trainingPlanExercises = uiState.trainingPlanExercises,
                                trainingPlan = uiState.trainingPlan,
                                pairExerciseAndParams = uiState.pairExerciseAndParams,
                                exercises = uiState.exercises
                            )
                        }
                        uiState.copy(
                            trainingPlanExercises = trainingPlanExercises,
                            pairExerciseAndParams = pairExerciseAndParams
                        )
                    }

                    dataStatus.ERROR -> {
                        _uiState.update {
                            ErrorState(
                                trainingPlanExercises = uiState.trainingPlanExercises,
                                trainingPlan = uiState.trainingPlan,
                                pairExerciseAndParams = uiState.pairExerciseAndParams,
                                exercises = uiState.exercises
                            )
                        }
                        uiState.copy(
                            trainingPlanExercises = trainingPlanExercises,
                            pairExerciseAndParams = pairExerciseAndParams
                        )
                    }

                }
            }

            is SuccessState -> {
                when (isLoading) {
                    dataStatus.LOADING -> {
                        _uiState.update {
                            LoadingState(
                                trainingPlanExercises = uiState.trainingPlanExercises,
                                trainingPlan = uiState.trainingPlan,
                                pairExerciseAndParams = uiState.pairExerciseAndParams,
                                exercises = uiState.exercises
                            )
                        }
                        uiState.copy(
                            trainingPlanExercises = trainingPlanExercises,
                            pairExerciseAndParams = pairExerciseAndParams
                        )
                    }

                    dataStatus.SUCCESS -> {

                        uiState.copy(
                            trainingPlanExercises = trainingPlanExercises,
                            pairExerciseAndParams = pairExerciseAndParams

                        )
                    }

                    dataStatus.ERROR -> {
                        _uiState.update {
                            ErrorState(
                                trainingPlanExercises = uiState.trainingPlanExercises,
                                trainingPlan = uiState.trainingPlan,
                                pairExerciseAndParams = uiState.pairExerciseAndParams,
                                exercises = uiState.exercises
                            )
                        }
                        uiState.copy(
                            trainingPlanExercises = trainingPlanExercises,
                            pairExerciseAndParams = pairExerciseAndParams
                        )
                    }

                }
            }

            is ErrorState -> {
                when (isLoading) {
                    dataStatus.LOADING -> {
                        _uiState.update {
                            LoadingState(
                                trainingPlanExercises = uiState.trainingPlanExercises,
                                trainingPlan = uiState.trainingPlan,
                                pairExerciseAndParams = uiState.pairExerciseAndParams,
                                exercises = uiState.exercises
                            )
                        }
                        uiState.copy(
                            trainingPlanExercises = trainingPlanExercises,
                            pairExerciseAndParams = pairExerciseAndParams
                        )
                    }

                    dataStatus.SUCCESS -> {
                        _uiState.update {
                            SuccessState(
                                trainingPlanExercises = uiState.trainingPlanExercises,
                                trainingPlan = uiState.trainingPlan,
                                pairExerciseAndParams = uiState.pairExerciseAndParams,
                                exercises = uiState.exercises
                            )
                        }
                        uiState.copy(
                            trainingPlanExercises = trainingPlanExercises,
                            pairExerciseAndParams = pairExerciseAndParams
                        )
                    }

                    dataStatus.ERROR ->
                        uiState.copy(
                            trainingPlanExercises = trainingPlanExercises,
                            pairExerciseAndParams = pairExerciseAndParams
                        )
                }
            }
        }
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000),
        LoadingState()
    )


    fun onEvent(event: TrainingPlanExercisesEvent) {
        when (_uiState.value) {
            is SuccessState -> {
                val state = _uiState.value as SuccessState
                when (event) {
                    is TrainingPlanExercisesEvent.UpdateCurrentTrainingPlanId -> {
                        _trainingPlanId.update {
                            event.id
                        }
                    }

                    is TrainingPlanExercisesEvent.AddTrainingPlanExercise -> {
                        val newTrainingPlanExercises: MutableList<Pair<Exercise, TrainingPlanExercise.TrainingPlanExerciseParams>> =
                            state.pairExerciseAndParams.toMutableList()
                        val trainingPlanExercise = Pair(
                            event.exercise,
                            TrainingPlanExerciseParams(15, true)
                        )
                        newTrainingPlanExercises.add(
                            index = event.insertedIndex,
                            element = trainingPlanExercise
                        )
                        _uiState.update {
                            state.copy(
                                pairExerciseAndParams = newTrainingPlanExercises
                            )
                        }
                    }

                    is TrainingPlanExercisesEvent.DeleteTrainingPlanExercise -> {
                        _uiState.update {
                            state.copy(
                                pairExerciseAndParams = it.pairExerciseAndParams.filterIndexed { index, _ ->
                                    index != event.index
                                }
                            )
                        }
                    }

                    is TrainingPlanExercisesEvent.EditTrainingPlanExercise -> {

                    }

                    is TrainingPlanExercisesEvent.SetInsertedIndexExercise -> {
                        _uiState.update {
                            state.copy(insertedIndexExercise = event.index)
                        }
                    }
                }
            }

            is LoadingState -> {
                val state = _uiState.value as LoadingState
                when (event) {
                    is TrainingPlanExercisesEvent.UpdateCurrentTrainingPlanId -> {
                        _trainingPlanId.update {
                            event.id
                        }
                    }

                    is TrainingPlanExercisesEvent.AddTrainingPlanExercise -> {
                        val newTrainingPlanExercises: MutableList<Pair<Exercise, TrainingPlanExercise.TrainingPlanExerciseParams>> =
                            state.pairExerciseAndParams.toMutableList()
                        val trainingPlanExercise = Pair(
                            event.exercise,
                            TrainingPlanExerciseParams(15, true)
                        )
                        newTrainingPlanExercises.add(
                            index = event.insertedIndex,
                            element = trainingPlanExercise
                        )
                        _uiState.update {
                            state.copy(
                                pairExerciseAndParams = newTrainingPlanExercises
                            )
                        }
                    }

                    is TrainingPlanExercisesEvent.DeleteTrainingPlanExercise -> {
                        _uiState.update {
                            state.copy(
                                pairExerciseAndParams = it.pairExerciseAndParams.filterIndexed { index, _ ->
                                    index != event.index
                                }
                            )
                        }
                    }

                    is TrainingPlanExercisesEvent.EditTrainingPlanExercise -> {

                    }
                }
            }
            is ErrorState -> {
                val state = _uiState.value as ErrorState
                when (event) {
                    is TrainingPlanExercisesEvent.UpdateCurrentTrainingPlanId -> {
                        _trainingPlanId.update {
                            event.id
                        }
                    }

                    is TrainingPlanExercisesEvent.AddTrainingPlanExercise -> {
                        val newTrainingPlanExercises: MutableList<Pair<Exercise, TrainingPlanExercise.TrainingPlanExerciseParams>> =
                            state.pairExerciseAndParams.toMutableList()
                        val trainingPlanExercise = Pair(
                            event.exercise,
                            TrainingPlanExercise.TrainingPlanExerciseParams(15, true)
                        )
                        newTrainingPlanExercises.add(
                            index = event.insertedIndex,
                            element = trainingPlanExercise
                        )
                        _uiState.update {
                            state.copy(
                                pairExerciseAndParams = newTrainingPlanExercises
                            )
                        }
                    }

                    is TrainingPlanExercisesEvent.DeleteTrainingPlanExercise -> {
                        _uiState.update {
                            state.copy(
                                pairExerciseAndParams = it.pairExerciseAndParams.filterIndexed { index, _ ->
                                    index != event.index
                                }
                            )
                        }
                    }

                    is TrainingPlanExercisesEvent.EditTrainingPlanExercise -> {

                    }
                }
            }
        }
    }
}