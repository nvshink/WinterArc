package com.nvshink.winterarc.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.serialization.Serializable


/**
 * A data class which represent exercise recorded in training plan
 * @param id Unique ID of an training plan exercise.
 * @param exercise Exercise object.
 * @param duration The duration indicates the time or number of approaches required to complete it.
 * @param isInSets Describes how the duration is measured. if true, then in approaches, otherwise in time.
 */
@Entity(
    tableName = "training_plan_exercise",
)
@Serializable
data class TrainingPlanExercise(
    @ColumnInfo(name = "exercise_id_foreign_key")
    val exerciseIdForeignKey: Int,
    @ColumnInfo(name = "duration")
    val duration: Int,
    @ColumnInfo(name = "is_in_sets")
    val isInSets: Boolean,
    @ColumnInfo(name = "training_plan_id_foreign_key")
    val trainingPlanIdForeignKey: Long,
    @ColumnInfo(name = "training_plan_exercise_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
