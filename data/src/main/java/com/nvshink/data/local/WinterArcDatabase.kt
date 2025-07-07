package com.nvshink.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nvshink.data.local.exercise.dao.ExerciseDao
import com.nvshink.data.local.exercise.entity.ExerciseEntity
import com.nvshink.data.local.trainingplan.dao.TrainingPlanDao
import com.nvshink.data.local.trainingplan.entity.TrainingPlanEntity
import com.nvshink.data.local.trainingplanexercise.dao.TrainingPlanExerciseDao
import com.nvshink.data.local.trainingplanexercise.entity.TrainingPlanExerciseEntity

@Database(
    entities = [ExerciseEntity::class, TrainingPlanEntity::class, TrainingPlanExerciseEntity::class],
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