package com.nvshink.winterarc.ui.states.exercise

import com.nvshink.domain.exercise.model.ExerciseImageModel
import com.nvshink.domain.exercise.model.ExerciseModel
import com.nvshink.winterarc.ui.utils.WinterArcContentType

interface ExerciseDetailUIState {
    val exercise: ExerciseModel?
    val contentType: WinterArcContentType

    data class LoadingState(
        override val exercise: ExerciseModel? = null,
        override val contentType: WinterArcContentType = WinterArcContentType.LIST_ONLY
    ) : ExerciseDetailUIState

    data class ViewState(
        override val exercise: ExerciseModel = ExerciseModel(name = "", description = "", imageLinks = emptyList()),
        override val contentType: WinterArcContentType = WinterArcContentType.LIST_ONLY
    ) : ExerciseDetailUIState

    data class AddState(
        override val exercise: ExerciseModel = ExerciseModel(name = "", description = "", imageLinks = emptyList()),
        override val contentType: WinterArcContentType = WinterArcContentType.LIST_ONLY,
        val name: String = "",
        val description: String = "",
        val imageLinks: List<ExerciseImageModel> = emptyList()
    ) : ExerciseDetailUIState

    data class EditState (
        override val exercise: ExerciseModel = ExerciseModel(name = "", description = "", imageLinks = emptyList()),
        override val contentType: WinterArcContentType = WinterArcContentType.LIST_ONLY,
        val name: String = exercise.name,
        val description: String = exercise.description,
        val imageLinks: List<ExerciseImageModel> = exercise.imageLinks
    ) : ExerciseDetailUIState

    data class ErrorState(
        override val exercise: ExerciseModel? = null,
        override val contentType: WinterArcContentType = WinterArcContentType.LIST_ONLY,
        val errorMessage: String = ""
    ) : ExerciseDetailUIState
}