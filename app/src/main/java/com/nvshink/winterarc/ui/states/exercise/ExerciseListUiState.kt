package com.nvshink.winterarc.ui.states.exercise

import com.nvshink.domain.exercise.model.ExerciseModel
import com.nvshink.winterarc.ui.utils.SortTypes

sealed interface ExerciseListUiState {
    val isShowingList: Boolean
    val sortType: SortTypes

    data class LoadingStateList(
        override val isShowingList: Boolean = true,
        override val sortType: SortTypes = SortTypes.NAME_ASC
    ) : ExerciseListUiState

    data class SuccessStateList(
        val exercisesMap: Map<Long, ExerciseModel> = emptyMap(),
        val currentExercise: ExerciseModel? = null,
        override val isShowingList: Boolean = true,
        override val sortType: SortTypes = SortTypes.NAME_ASC
    ) : ExerciseListUiState

    data class ErrorStateList(
        override val isShowingList: Boolean = true,
        override val sortType: SortTypes = SortTypes.NAME_ASC
    ) : ExerciseListUiState
}