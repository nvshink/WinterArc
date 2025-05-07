package com.nvshink.winterarc.ui.states

import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.ui.utils.SortTypes

sealed interface ExerciseUiState {
    val exercisesMap: MutableMap<Int, Exercise>
    val currentExercise: Exercise?
    val name: String
    val description: String
    val images: List<String>
    val isShowingList: Boolean
    val isAddingExercise: Boolean
    val isBigScreen: Boolean
    val isShowingEditDialog: Boolean
    val sortType: SortTypes
    data class Loading (
        override val exercisesMap: MutableMap<Int, Exercise> = mutableMapOf(),
        override val currentExercise: Exercise? = null,
        override val name: String = "",
        override val description: String = "",
        override val images: List<String> = emptyList(),
        override val isShowingList: Boolean = true,
        override val isAddingExercise: Boolean = true,
        override val isBigScreen: Boolean = false,
        override val isShowingEditDialog: Boolean = false,
        override val sortType: SortTypes = SortTypes.NAME
    ): ExerciseUiState
    data class Success (
        override val exercisesMap: MutableMap<Int, Exercise> = mutableMapOf(),
        override val currentExercise: Exercise? = null,
        override val name: String = "",
        override val description: String = "",
        override val images: List<String> = emptyList(),
        override val isShowingList: Boolean = true,
        override val isAddingExercise: Boolean = true,
        override val isBigScreen: Boolean = false,
        override val isShowingEditDialog: Boolean = false,
        override val sortType: SortTypes = SortTypes.NAME
    ): ExerciseUiState
    data class Error (
        override val exercisesMap: MutableMap<Int, Exercise> = mutableMapOf(),
        override val currentExercise: Exercise? = null,
        override val name: String = "",
        override val description: String = "",
        override val images: List<String> = emptyList(),
        override val isShowingList: Boolean = true,
        override val isAddingExercise: Boolean = true,
        override val isBigScreen: Boolean = false,
        override val isShowingEditDialog: Boolean = false,
        override val sortType: SortTypes = SortTypes.NAME
    ): ExerciseUiState
}