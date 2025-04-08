package com.nvshink.winterarc.ui.event

import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.ui.utils.SortTypes

sealed interface ExerciseEvent {
    data object SaveExercise: ExerciseEvent
    data object ShowDialog: ExerciseEvent
    data object HideDialog: ExerciseEvent
    data object ShowList: ExerciseEvent
    data object HideList: ExerciseEvent
    data class UpdateCurrentExercise (val exercise: Exercise): ExerciseEvent
    data class SetName (val name: String): ExerciseEvent
    data class SetDescription (val description: String): ExerciseEvent
    data class SetImages (val images: List<String>): ExerciseEvent
    data class SortExercises(val sortType:SortTypes):ExerciseEvent
    data class DeleteExercise(val exercise: Exercise):ExerciseEvent
}