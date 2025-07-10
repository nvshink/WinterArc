package com.nvshink.winterarc.ui.event.exercise

import com.nvshink.domain.exercise.model.ExerciseModel
import com.nvshink.winterarc.ui.utils.SortTypes
import com.nvshink.winterarc.ui.utils.WinterArcContentType

sealed interface ExerciseListEvent {
    data object ShowList : ExerciseListEvent
    data object HideList : ExerciseListEvent
    data class UpdateCurrentExercise(val exercise: ExerciseModel?) : ExerciseListEvent
    data class SortExercises(val sortType: SortTypes) : ExerciseListEvent
    data class SetContentType(val contentType: WinterArcContentType) : ExerciseListEvent
}