package com.nvshink.winterarc.ui.event

import android.content.Context
import com.nvshink.domain.exercise.model.ExerciseModel
import com.nvshink.winterarc.ui.utils.SortTypes

sealed interface ExerciseEvent {
    data class SaveExercise(val context: Context, val exercise: ExerciseModel): ExerciseEvent
    data object ShowList: ExerciseEvent
    data object HideList: ExerciseEvent
    data class SetIsBigScreen (val isBigScreen: Boolean): ExerciseEvent
    data class UpdateCurrentExercise (val exercise: ExerciseModel?): ExerciseEvent
    data class SetName (val name: String): ExerciseEvent
    data class SetDescription (val description: String): ExerciseEvent
    data class SetImages (val images: List<String>): ExerciseEvent
    data class SortExercises(val sortType:SortTypes):ExerciseEvent
    data class DeleteExercise(val exercise: ExerciseModel):ExerciseEvent
}