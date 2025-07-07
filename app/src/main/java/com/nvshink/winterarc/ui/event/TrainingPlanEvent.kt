package com.nvshink.winterarc.ui.event

import com.nvshink.domain.trainingplan.model.TrainingPlanModel
import com.nvshink.winterarc.ui.utils.SortTypes

sealed interface TrainingPlanEvent {
    data object SaveTrainingPlan: TrainingPlanEvent
    data class DeleteTrainingPlan(val trainingPlan: TrainingPlanModel):TrainingPlanEvent
    data class ShowDialog(val isAdding: Boolean): TrainingPlanEvent
    data object HideDialog: TrainingPlanEvent
    data object ShowList: TrainingPlanEvent
    data object HideList: TrainingPlanEvent
    data object ShowExerciseSelector: TrainingPlanEvent
    data object HideExerciseSelector: TrainingPlanEvent
    data class SetIsBigScreen (val isBigScreen: Boolean): TrainingPlanEvent
    data class UpdateCurrentTrainingPlan (val trainingPlan: TrainingPlanModel?): TrainingPlanEvent
    data class SetName (val name: String): TrainingPlanEvent
    data class SetDescription (val description: String): TrainingPlanEvent
    data class SortTrainingPlan(val sortType: SortTypes):TrainingPlanEvent
}