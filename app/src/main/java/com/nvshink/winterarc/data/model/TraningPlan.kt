package com.nvshink.winterarc.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.serialization.Serializable

/**
 * A class which represent training plan.
 * @param id Unique ID of an exercise.
 * @param name Name of the exercise.
 * @param description Description of the correct exercise performance.
 * @param exercises Contain list of exercises that recorded in this training plan.
 */
@Entity(tableName = "training_plan")
@Serializable
data class TrainingPlan(
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "training_plan_id")
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)

data class TrainingPlanWithTrainingPlanExercises(
    @Embedded val trainingPlan: TrainingPlan,
    @Relation(
        parentColumn = "training_plan_id",
        entityColumn  = "training_plan_exercise_id"
    )
    val trainingPlanExercises: List<TrainingPlanExercise>
)

