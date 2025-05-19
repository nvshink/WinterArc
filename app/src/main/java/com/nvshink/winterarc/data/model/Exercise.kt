package com.nvshink.winterarc.data.model

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverters
import com.nvshink.winterarc.data.room.Converters
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

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
    val images: List<String>,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "exercise_id")
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)

data class ExerciseWithTrainingPlanExercises(
    @Embedded val exercise: Exercise,
    @Relation(
        parentColumn = "exercise_id",
        entityColumn = "training_plan_exercise_id"
    )
    val trainingPlanExercises: List<TrainingPlanExercise>
)