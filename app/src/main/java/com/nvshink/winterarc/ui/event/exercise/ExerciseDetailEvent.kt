package com.nvshink.winterarc.ui.event.exercise

import android.content.Context
import com.nvshink.domain.exercise.model.ExerciseImageModel
import com.nvshink.domain.exercise.model.ExerciseModel
import com.nvshink.winterarc.ui.utils.WinterArcContentType

sealed interface ExerciseDetailEvent {
    data class SetExercise(val id: Long) : ExerciseDetailEvent
    data class SetName(val name: String) : ExerciseDetailEvent
    data class SetDescription(val description: String) : ExerciseDetailEvent
    data class SetImages(val images: List<ExerciseImageModel>) : ExerciseDetailEvent
    data class SaveExercise(val exercise: ExerciseModel) : ExerciseDetailEvent
    data class DeleteExercise(val exercise: ExerciseModel) : ExerciseDetailEvent
    data class SetContentType(val contentType: WinterArcContentType) : ExerciseDetailEvent
    object EditExercise : ExerciseDetailEvent
    object ViewExercise : ExerciseDetailEvent
    object AddExercise : ExerciseDetailEvent
}