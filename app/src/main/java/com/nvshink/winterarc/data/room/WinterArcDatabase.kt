package com.nvshink.winterarc.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.data.model.TrainingPlan
import com.nvshink.winterarc.data.model.TrainingPlanExercise

@Database(
    entities = [Exercise::class, TrainingPlan::class, TrainingPlanExercise::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    Converters::class
)
abstract class WinterArcDatabase: RoomDatabase() {
    abstract val exerciseDao: ExerciseDao
    abstract val trainingPlanDao: TrainingPlanDao
    abstract val trainingPlanExerciseDao: TrainingPlanExerciseDao
}