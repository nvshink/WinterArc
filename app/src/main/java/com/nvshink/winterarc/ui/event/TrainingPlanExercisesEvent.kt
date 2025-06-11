package com.nvshink.winterarc.ui.event

import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.data.local.trainingplanexercise.entity.TrainingPlanExercise.TrainingPlanExerciseParams

interface TrainingPlanExercisesEvent {
    data class UpdateCurrentTrainingPlanId (val id: Long?): TrainingPlanExercisesEvent
    data class AddTrainingPlanExercise (val insertedIndex: Int, val exercise: Exercise): TrainingPlanExercisesEvent
    data class DeleteTrainingPlanExercise (val index: Int): TrainingPlanExercisesEvent
    data class EditTrainingPlanExercise (val trainingPlanExercises: List<Pair<Exercise, TrainingPlanExerciseParams>>): TrainingPlanExercisesEvent
    data class SetInsertedIndexExercise (val index: Int): TrainingPlanExercisesEvent
}