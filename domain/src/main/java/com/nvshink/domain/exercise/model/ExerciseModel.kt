package com.nvshink.domain.exercise.model


data class ExerciseModel(
    val id: Long = 0,
    val name: String,
    val imageLinks: List<ExerciseImageModel>,
    val description: String,
)