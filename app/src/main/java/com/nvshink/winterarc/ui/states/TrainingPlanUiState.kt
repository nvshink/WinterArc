package com.nvshink.winterarc.ui.states

import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.data.model.TrainingPlan
import com.nvshink.winterarc.data.repository.TrainingPlanRepository
import com.nvshink.winterarc.ui.utils.SortTypes

sealed interface TrainingPlanUiState {
    val currentTrainingPlan: TrainingPlan?
    val name: String
    val description: String
    val trainingPlanExercises: List<Pair<Exercise, TrainingPlanRepository.TrainingPlanExerciseParams>>
    val isShowingList: Boolean
    val isAddingTrainingPlan: Boolean
    val isBigScreen: Boolean
    val isShowingEditDialog: Boolean
    val sortType: SortTypes

    data class LoadingState(
        override val currentTrainingPlan: TrainingPlan? = null,
        override val name: String = "",
        override val description: String = "",
        override val trainingPlanExercises: List<Pair<Exercise, TrainingPlanRepository.TrainingPlanExerciseParams>> = emptyList(),
        override val isShowingList: Boolean = true,
        override val isAddingTrainingPlan: Boolean = true,
        override val isBigScreen: Boolean = false,
        override val isShowingEditDialog: Boolean = false,
        override val sortType: SortTypes = SortTypes.NAME_ASC
    ) : TrainingPlanUiState

    data class SuccessState(
        val trainingPlansMap: Map<Long, TrainingPlan> = mutableMapOf(),
        override val currentTrainingPlan: TrainingPlan? = null,
        override val name: String = "",
        override val description: String = "",
        override val trainingPlanExercises: List<Pair<Exercise, TrainingPlanRepository.TrainingPlanExerciseParams>> = emptyList(),
        override val isShowingList: Boolean = true,
        override val isAddingTrainingPlan: Boolean = true,
        override val isBigScreen: Boolean = false,
        override val isShowingEditDialog: Boolean = false,
        val isShowingExerciseSelector: Boolean = false,
        override val sortType: SortTypes = SortTypes.NAME_ASC
    ) : TrainingPlanUiState

    data class ErrorState(
        override val currentTrainingPlan: TrainingPlan? = null,
        override val name: String = "",
        override val description: String = "",
        override val trainingPlanExercises: List<Pair<Exercise, TrainingPlanRepository.TrainingPlanExerciseParams>> = emptyList(),
        override val isShowingList: Boolean = true,
        override val isAddingTrainingPlan: Boolean = true,
        override val isBigScreen: Boolean = false,
        override val isShowingEditDialog: Boolean = false,
        override val sortType: SortTypes = SortTypes.NAME_ASC
    ) : TrainingPlanUiState
}