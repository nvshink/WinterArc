package com.nvshink.winterarc.data.local.exercise.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
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
data class ExerciseEntity(
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