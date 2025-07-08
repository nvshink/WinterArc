package com.nvshink.winterarc.ui.states.exercise

import com.nvshink.domain.exercise.model.ExerciseModel

interface ExerciseDetailUIState {
    val exercise: ExerciseModel
    data class LoadingState(
        override val exercise: ExerciseModel = ExerciseModel(name = "", description = "", imageLinks = emptyList())
    ) : ExerciseDetailUIState

    data class ViewState(
        override val exercise: ExerciseModel = ExerciseModel(name = "", description = "", imageLinks = emptyList()),
        val name: String = exercise.name,
        val description: String = exercise.description,
        val imageLinks: List<String> = exercise.imageLinks
    ) : ExerciseDetailUIState

    data class AddState(
        override val exercise: ExerciseModel = ExerciseModel(name = "", description = "", imageLinks = emptyList()),
        val name: String = "",
        val description: String = "",
        val imageLinks: List<String> = emptyList()
    ) : ExerciseDetailUIState

    data class EditState (
        override val exercise: ExerciseModel = ExerciseModel(name = "", description = "", imageLinks = emptyList()),
        val name: String = exercise.name,
        val description: String = exercise.description,
        val imageLinks: List<String> = exercise.imageLinks
    ) : ExerciseDetailUIState

    data class ErrorState(
        override val exercise: ExerciseModel = ExerciseModel(name = "", description = "", imageLinks = emptyList())
    ) : ExerciseDetailUIState
}