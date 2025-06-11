package com.nvshink.winterarc.di

import com.nvshink.winterarc.data.local.exercise.repository.ExerciseRepository
import com.nvshink.winterarc.data.local.trainingplan.repository.TrainingPlanRepository
import com.nvshink.winterarc.data.local.exercise.dao.ExerciseDao
import com.nvshink.winterarc.data.local.trainingplan.dao.TrainingPlanDao
import com.nvshink.winterarc.data.local.trainingplanexercise.dao.TrainingPlanExerciseDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {


    @Binds
    @Singleton
    fun provideExerciseRepository(dao: ExerciseDao): ExerciseRepository = ExerciseRepository(dao = dao)

    @Binds
    @Singleton
    fun provideTrainingPlanRepository(trainingPlanDao: TrainingPlanDao, trainingPlanExerciseDao: TrainingPlanExerciseDao): TrainingPlanRepository = TrainingPlanRepository(trainingPlanDao = trainingPlanDao, trainingPlanExerciseDao = trainingPlanExerciseDao)
}