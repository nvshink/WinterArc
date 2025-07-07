package com.nvshink.winterarc.ui.states

import com.nvshink.domain.exercise.model.ExerciseModel
import com.nvshink.domain.trainingplan.model.TrainingPlanModel
import com.nvshink.domain.trainingplanexercise.model.TrainingPlanExerciseModel

sealed interface TrainingPlanExerciseUiState {
    val trainingPlanExercises: List<TrainingPlanExerciseModel>
//    val pairExerciseAndParams: List<Pair<ExerciseModel, TrainingPlanExerciseParams>>
    val TrainingPlanModel: TrainingPlanModel?
//    val exercises: Pair<ExerciseModel, TrainingPlanExerciseParams>?

    data class LoadingState(
        override val trainingPlanExercises: List<TrainingPlanExerciseModel> = emptyList(),
//        override val pairExerciseAndParams: List<Pair<ExerciseModel, TrainingPlanExerciseParams>> = emptyList(),
        override val TrainingPlanModel: TrainingPlanModel? = null,
//        override val exercises: Pair<ExerciseModel, TrainingPlanExerciseParams>? = null
    ):TrainingPlanExerciseUiState

    data class SuccessState(
        override val trainingPlanExercises: List<TrainingPlanExerciseModel> = emptyList(),
//        override val pairExerciseAndParams: List<Pair<ExerciseModel, TrainingPlanExerciseParams>> = emptyList(),
        override val TrainingPlanModel: TrainingPlanModel? = null,
//        override val exercises: Pair<ExerciseModel, TrainingPlanExerciseParams>? = null,
        val insertedIndexExercise: Int = 0,
    ):TrainingPlanExerciseUiState

    data class ErrorState(
        override val trainingPlanExercises: List<TrainingPlanExerciseModel> = emptyList(),
//        override val pairExerciseAndParams: List<Pair<ExerciseModel, TrainingPlanExerciseParams>> = emptyList(),
        override val TrainingPlanModel: TrainingPlanModel? = null,
//        override val exercises: Pair<ExerciseModel, TrainingPlanExerciseParams>? = null
    ):TrainingPlanExerciseUiState
}