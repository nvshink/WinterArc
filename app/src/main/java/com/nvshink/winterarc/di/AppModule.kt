package com.nvshink.winterarc.di

import android.content.Context
import androidx.room.Room
import com.nvshink.winterarc.data.repository.ExerciseRepository
import com.nvshink.winterarc.data.repository.TrainingPlanExerciseRepository
import com.nvshink.winterarc.data.repository.TrainingPlanRepository
import com.nvshink.winterarc.data.room.ExerciseDao
import com.nvshink.winterarc.data.room.TrainingPlanDao
import com.nvshink.winterarc.data.room.TrainingPlanExerciseDao
import com.nvshink.winterarc.data.room.WinterArcDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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
    fun provideExerciseRepository(dao: ExerciseDao): ExerciseRepository = ExerciseRepository(dao = dao)

    @Provides
    @Singleton
    fun provideTrainingPlanDao(db: WinterArcDatabase): TrainingPlanDao = db.trainingPlanDao
    @Provides
    @Singleton
    fun provideTrainingPlanRepository(dao: TrainingPlanDao): TrainingPlanRepository = TrainingPlanRepository(dao = dao)

    @Provides
    @Singleton
    fun provideTrainingPlanExerciseDao(db: WinterArcDatabase): TrainingPlanExerciseDao = db.trainingPlanExerciseDao
    @Provides
    @Singleton
    fun provideTrainingPlanExerciseRepository(dao: TrainingPlanExerciseDao): TrainingPlanExerciseRepository = TrainingPlanExerciseRepository(dao = dao)
}