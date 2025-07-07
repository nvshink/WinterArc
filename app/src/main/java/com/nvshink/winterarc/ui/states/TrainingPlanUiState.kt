package com.nvshink.winterarc.ui.states

import com.nvshink.domain.trainingplan.model.TrainingPlanModel
import com.nvshink.winterarc.ui.utils.SortTypes

sealed interface TrainingPlanUiState {
    val currentTrainingPlan: TrainingPlanModel?
    val name: String
    val description: String
//    val trainingPlanExercises: List<TrainingPlanExercise>
    val isShowingList: Boolean
    val isAddingTrainingPlan: Boolean
    val isBigScreen: Boolean
    val isShowingEditDialog: Boolean
    val sortType: SortTypes

    data class LoadingState(
        override val currentTrainingPlan: TrainingPlanModel? = null,
        override val name: String = "",
        override val description: String = "",
//        override val trainingPlanExercises: List<TrainingPlanExercise> = emptyList(),
        override val isShowingList: Boolean = true,
        override val isAddingTrainingPlan: Boolean = true,
        override val isBigScreen: Boolean = false,
        override val isShowingEditDialog: Boolean = false,
        override val sortType: SortTypes = SortTypes.NAME_ASC
    ) : TrainingPlanUiState

    data class SuccessState(
        val trainingPlansMap: Map<Long, TrainingPlanModel> = mutableMapOf(),
        override val currentTrainingPlan: TrainingPlanModel? = null,
        override val name: String = "",
        override val description: String = "",
//        override val trainingPlanExercises: List<TrainingPlanExercise> = emptyList(),
        override val isShowingList: Boolean = true,
        override val isAddingTrainingPlan: Boolean = true,
        override val isBigScreen: Boolean = false,
        override val isShowingEditDialog: Boolean = false,
        val isShowingExerciseSelector: Boolean = false,
        override val sortType: SortTypes = SortTypes.NAME_ASC
    ) : TrainingPlanUiState

    data class ErrorState(
        override val currentTrainingPlan: TrainingPlanModel? = null,
        override val name: String = "",
        override val description: String = "",
//        override val trainingPlanExercises: List<TrainingPlanExercise> = emptyList(),
        override val isShowingList: Boolean = true,
        override val isAddingTrainingPlan: Boolean = true,
        override val isBigScreen: Boolean = false,
        override val isShowingEditDialog: Boolean = false,
        override val sortType: SortTypes = SortTypes.NAME_ASC
    ) : TrainingPlanUiState
}