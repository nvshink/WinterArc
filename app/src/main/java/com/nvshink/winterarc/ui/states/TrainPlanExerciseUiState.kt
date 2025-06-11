package com.nvshink.winterarc.ui.states

import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.data.local.trainingplan.TrainingPlan
import com.nvshink.winterarc.data.local.trainingplanexercise.entity.TrainingPlanExercise
import com.nvshink.winterarc.data.local.trainingplanexercise.entity.TrainingPlanExercise.TrainingPlanExerciseParams

sealed interface TrainingPlanExerciseUiState {
    val trainingPlanExercises: List<TrainingPlanExercise>
    val pairExerciseAndParams: List<Pair<Exercise, TrainingPlanExerciseParams>>
    val trainingPlan: TrainingPlan?
    val exercises: Pair<Exercise, TrainingPlanExerciseParams>?

    data class LoadingState(
        override val trainingPlanExercises: List<TrainingPlanExercise> = emptyList(),
        override val pairExerciseAndParams: List<Pair<Exercise, TrainingPlanExerciseParams>> = emptyList(),
        override val trainingPlan: TrainingPlan? = null,
        override val exercises: Pair<Exercise, TrainingPlanExerciseParams>? = null
    ):TrainingPlanExerciseUiState

    data class SuccessState(
        override val trainingPlanExercises: List<TrainingPlanExercise> = emptyList(),
        override val pairExerciseAndParams: List<Pair<Exercise, TrainingPlanExerciseParams>> = emptyList(),
        override val trainingPlan: TrainingPlan? = null,
        override val exercises: Pair<Exercise, TrainingPlanExerciseParams>? = null,
        val insertedIndexExercise: Int = 0,
    ):TrainingPlanExerciseUiState

    data class ErrorState(
        override val trainingPlanExercises: List<TrainingPlanExercise> = emptyList(),
        override val pairExerciseAndParams: List<Pair<Exercise, TrainingPlanExerciseParams>> = emptyList(),
        override val trainingPlan: TrainingPlan? = null,
        override val exercises: Pair<Exercise, TrainingPlanExerciseParams>? = null
    ):TrainingPlanExerciseUiState
}