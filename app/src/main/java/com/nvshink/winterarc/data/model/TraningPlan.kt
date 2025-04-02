package com.nvshink.winterarc.data.model

import kotlinx.serialization.Serializable

/**
 * A class which represent training plan.
 * @param id Unique ID of an exercise.
 * @param name Name of the exercise.
 * @param description Description of the correct exercise performance.
 * @param exercises Contain list of exercises that recorded in this training plan.
 */
@Serializable
data class TrainingPlan(
    val id: Int,
    var name: String,
    var description: String,
    var exercises: List<TrainingPlanExercise>
)
