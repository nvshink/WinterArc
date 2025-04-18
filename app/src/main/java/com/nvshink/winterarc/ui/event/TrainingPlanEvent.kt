package com.nvshink.winterarc.ui.event

import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.data.model.TrainingPlan
import com.nvshink.winterarc.data.repository.TrainingPlanRepository.TrainingPlanExerciseParams
import com.nvshink.winterarc.ui.utils.SortTypes

sealed interface TrainingPlanEvent {
    data object SaveTrainingPlan: TrainingPlanEvent
    data class DeleteTrainingPlan(val trainingPlan: TrainingPlan):TrainingPlanEvent
    data class ShowDialog(val isAdding: Boolean): TrainingPlanEvent
    data object HideDialog: TrainingPlanEvent
    data object ShowList: TrainingPlanEvent
    data object HideList: TrainingPlanEvent
    data class SetIsBigScreen (val isBigScreen: Boolean): TrainingPlanEvent
    data class UpdateCurrentTrainingPlan (val trainingPlan: TrainingPlan): TrainingPlanEvent
    data class SetName (val name: String): TrainingPlanEvent
    data class SetDescription (val description: String): TrainingPlanEvent
    data class SetTrainingPlanExercises (val trainingPlanExercises: List<Pair<Exercise, TrainingPlanExerciseParams>>): TrainingPlanEvent
    data class SortTrainingPlan(val sortType: SortTypes):TrainingPlanEvent
}