package com.nvshink.winterarc.ui.states.exercise

import com.nvshink.domain.exercise.model.ExerciseModel
import com.nvshink.winterarc.ui.utils.SortTypes
import com.nvshink.winterarc.ui.utils.WinterArcContentType

sealed interface ExerciseListUiState {
    val isShowingList: Boolean
    val sortType: SortTypes
    val contentType: WinterArcContentType

    data class LoadingStateList(
        override val isShowingList: Boolean = true,
        override val sortType: SortTypes = SortTypes.NAME_ASC,
        override val contentType: WinterArcContentType = WinterArcContentType.LIST_ONLY
    ) : ExerciseListUiState

    data class SuccessStateList(
        val exercisesMap: Map<Long, ExerciseModel> = emptyMap(),
        val currentExercise: ExerciseModel? = null,
        override val isShowingList: Boolean = true,
        override val sortType: SortTypes = SortTypes.NAME_ASC,
        override val contentType: WinterArcContentType = WinterArcContentType.LIST_ONLY
    ) : ExerciseListUiState

    data class ErrorStateList(
        override val isShowingList: Boolean = true,
        override val sortType: SortTypes = SortTypes.NAME_ASC,
        override val contentType: WinterArcContentType = WinterArcContentType.LIST_ONLY
    ) : ExerciseListUiState
}