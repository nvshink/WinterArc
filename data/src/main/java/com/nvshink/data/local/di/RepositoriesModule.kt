package com.nvshink.data.local.di

import com.nvshink.data.local.exercise.dao.ExerciseDao
import com.nvshink.data.local.exercise.repository.ExerciseRepositoryImpl
import com.nvshink.data.local.trainingplan.dao.TrainingPlanDao
import com.nvshink.data.local.trainingplan.repository.TrainingPlanRepositoryImpl
import com.nvshink.data.local.trainingplanexercise.dao.TrainingPlanExerciseDao
import com.nvshink.data.local.trainingplanexercise.repository.TrainingPlanExerciseRepositoryImpl
import com.nvshink.domain.exercise.repository.ExerciseRepository
import com.nvshink.domain.trainingplan.repository.TrainingPlanRepository
import com.nvshink.domain.trainingplanexercise.repository.TrainingPlanExerciseRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    @Singleton
    abstract fun provideExerciseRepository(exerciseRepositoryImpl: ExerciseRepositoryImpl): ExerciseRepository

    @Binds
    @Singleton
    abstract fun provideTrainingPlanRepository(trainingPlanRepositoryImpl: TrainingPlanRepositoryImpl): TrainingPlanRepository

    @Binds
    @Singleton
    abstract fun provideTrainingPlanExerciseRepository(trainingPlanExerciseRepositoryImpl: TrainingPlanExerciseRepositoryImpl): TrainingPlanExerciseRepository
}