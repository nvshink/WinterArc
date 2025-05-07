package com.nvshink.winterarc.ui.states

import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.data.model.TrainingPlan
import com.nvshink.winterarc.data.repository.TrainingPlanRepository
import com.nvshink.winterarc.ui.utils.SortTypes

data class TrainingPlanUiState(
    val trainingPlansMap: MutableMap<Int, TrainingPlan> = mutableMapOf(),
    val currentTrainingPlan: TrainingPlan? = null,
    val name: String = "",
    val description: String = "",
    val trainingPlanExercises: List<Pair<Exercise, TrainingPlanRepository.TrainingPlanExerciseParams>> = emptyList(),
    val isShowingList: Boolean = true,
    val isAddingTrainingPlan: Boolean = true,
    val isBigScreen: Boolean = false,
    val isShowingEditDialog: Boolean = false,
    val sortType: SortTypes = SortTypes.NAME
)