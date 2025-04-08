package com.nvshink.winterarc.ui.event

import com.nvshink.winterarc.data.model.TrainingPlan
import com.nvshink.winterarc.ui.utils.SortTypes

sealed interface TrainingPlanEvent {
    data object SaveTrainingPlan: TrainingPlanEvent
    data object ShowDialog: TrainingPlanEvent
    data object HideDialog: TrainingPlanEvent
    data object ShowList: TrainingPlanEvent
    data object HideList: TrainingPlanEvent
    data class UpdateCurrentTrainingPlan (val trainingPlan: TrainingPlan): TrainingPlanEvent
    data class SetName (val name: String): TrainingPlanEvent
    data class SetDescription (val description: String): TrainingPlanEvent
    data class SetImages (val images: List<String>): TrainingPlanEvent
    data class SetTrainingPlanExercises (val trainingPlanExercisesIds: List<String>): TrainingPlanEvent
    data class SortTrainingPlan(val sortType: SortTypes):TrainingPlanEvent
    data class DeleteTrainingPlan(val trainingPlan: TrainingPlan):TrainingPlanEvent
}