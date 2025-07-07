package com.nvshink.data.local.di

import android.content.Context
import androidx.room.Room
import com.nvshink.data.local.WinterArcDatabase
import com.nvshink.data.local.exercise.dao.ExerciseDao
import com.nvshink.data.local.trainingplan.dao.TrainingPlanDao
import com.nvshink.data.local.trainingplanexercise.dao.TrainingPlanExerciseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): WinterArcDatabase {
        return Room.databaseBuilder(
            context,
            WinterArcDatabase::class.java,
            "local_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideExerciseDao(db: WinterArcDatabase): ExerciseDao = db.exerciseDao

    @Provides
    @Singleton
    fun provideTrainingPlanDao(db: WinterArcDatabase): TrainingPlanDao = db.trainingPlanDao

    @Provides
    @Singleton
    fun provideTrainingPlanExerciseDao(db: WinterArcDatabase): TrainingPlanExerciseDao = db.trainingPlanExerciseDao
}