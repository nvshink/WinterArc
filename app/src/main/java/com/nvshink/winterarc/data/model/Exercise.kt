package com.nvshink.winterarc.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.serialization.Serializable

/**
 * A class which represent exercise.
 * @param id Unique ID of an exercise.
 * @param name Name of the exercise.
 * @param images Images with examples of the exercise.
 * @param description Description of the correct exercise performance.
 */
@Entity(tableName = "exercise")
@Serializable
data class Exercise(
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "images")
    val images: List<String>?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "exercise_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

data class ExerciseWithTrainingPlanExercises(
    @Embedded val exercise: Exercise,
    @Relation(
        parentColumn = "exercise_id",
        entityColumn = "training_plan_exercise_id"
    )
    val trainingPlanExercises: List<TrainingPlanExercise>
)

