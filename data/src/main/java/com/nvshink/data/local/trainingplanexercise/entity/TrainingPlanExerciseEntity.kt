package com.nvshink.data.local.trainingplanexercise.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * A data class which represent exercise recorded in training plan
 * @param exerciseIdForeignKey Unique ID of an related exercise.
 * @param duration The duration indicates the time or number of approaches required to complete it.
 * @param isInSets Describes how the duration is measured. if true, then in approaches, otherwise in time.
 * @param trainingPlanIdForeignKey Unique ID of an related training plan.
 * @param id Unique ID of an training plan exercise.
 */
@Entity(
    tableName = "training_plan_exercise",
)
data class TrainingPlanExerciseEntity(
    @ColumnInfo(name = "exercise_id_foreign_key")
    val exerciseIdForeignKey: Long,
    @ColumnInfo(name = "duration")
    val duration: Int,
    @ColumnInfo(name = "is_in_sets")
    val isInSets: Boolean,
    @ColumnInfo(name = "training_plan_id_foreign_key")
    val trainingPlanIdForeignKey: Long,
    @ColumnInfo(name = "training_plan_exercise_id")
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
) {
    /**
     * Supported class to contain exercise params
     * @param duration Exercise duration. Use for sets and time exercises.
     * @param isInSets Define duration as sets or as seconds
     */
    data class TrainingPlanExerciseParams(
        val duration: Int,
        val isInSets: Boolean,
    )
}
