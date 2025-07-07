package com.nvshink.domain.trainingplanexercise.model

data class TrainingPlanExerciseModel(
    val id: Long = 0,
    val exerciseId: Long?,
    val trainingPlanId: Long?,
    val duration: Int,
    val isInSets: Boolean,
)