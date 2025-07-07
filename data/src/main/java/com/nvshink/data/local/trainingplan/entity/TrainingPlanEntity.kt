package com.nvshink.data.local.trainingplan.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A class which represent training plan.
 * @param id Unique ID of an training plan.
 * @param name Name of the training plan.
 * @param description Description of the training plan.
 */
@Entity(tableName = "training_plan")
data class TrainingPlanEntity(
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "description")
    var description: String,
    @ColumnInfo(name = "training_plan_id")
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)

