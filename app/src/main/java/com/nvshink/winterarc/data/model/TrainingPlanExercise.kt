package com.nvshink.winterarc.data.model

import kotlinx.serialization.Serializable


/**
 * A data class which represent exercise recorded in training plan
 * @param id Unique ID of an training plan exercise.
 * @param exercise Exercise object.
 * @param duration The duration indicates the time or number of approaches required to complete it.
 * @param isInSets Describes how the duration is measured. if true, then in approaches, otherwise in time.
 */
@Serializable
data class TrainingPlanExercise (
    val id: Int,
    val exercise: Exercise,
    val duration: Int,
    val isInSets: Boolean
)