package com.example.winterarc.data.model

import kotlinx.serialization.Serializable

/**
 * A class which represent exercise.
 * @param id Unique ID of an exercise.
 * @param name Name of the exercise.
 * @param images Images with examples of the exercise.
 * @param description Description of the correct exercise performance.
 */
@Serializable
data class Exercise(
    val id: Int,
    val name: String,
    val images: List<String>,
    val description: String,
)

