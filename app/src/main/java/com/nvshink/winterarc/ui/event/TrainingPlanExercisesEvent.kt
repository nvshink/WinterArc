package com.nvshink.winterarc.ui.event

import com.nvshink.domain.exercise.model.ExerciseModel

interface TrainingPlanExercisesEvent {
    data class UpdateCurrentTrainingPlanId (val id: Long?): TrainingPlanExercisesEvent
    data class AddTrainingPlanExercise (val insertedIndex: Int, val exercise: ExerciseModel): TrainingPlanExercisesEvent
    data class DeleteTrainingPlanExercise (val index: Int): TrainingPlanExercisesEvent
//    data class EditTrainingPlanExercise (val trainingPlanExercises: List<Pair<Exercise, TrainingPlanExerciseParams>>): TrainingPlanExercisesEvent
    data class SetInsertedIndexExercise (val index: Int): TrainingPlanExercisesEvent
}