package com.nvshink.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nvshink.winterarc.data.local.exercise.dao.ExerciseDao
import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.data.local.trainingplan.TrainingPlan
import com.nvshink.winterarc.data.local.trainingplanexercise.entity.TrainingPlanExercise
import com.nvshink.winterarc.data.local.trainingplan.dao.TrainingPlanDao
import com.nvshink.winterarc.data.local.trainingplanexercise.dao.TrainingPlanExerciseDao

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