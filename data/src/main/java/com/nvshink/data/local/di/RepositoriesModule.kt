package com.nvshink.data.local.di

import com.nvshink.data.local.exercise.dao.ExerciseDao
import com.nvshink.data.local.exercise.repository.ExerciseRepositoryImpl
import com.nvshink.data.local.localstorage.LocalStorageImpl
import com.nvshink.data.local.trainingplan.dao.TrainingPlanDao
import com.nvshink.data.local.trainingplan.repository.TrainingPlanRepositoryImpl
import com.nvshink.data.local.trainingplanexercise.dao.TrainingPlanExerciseDao
import com.nvshink.data.local.trainingplanexercise.repository.TrainingPlanExerciseRepositoryImpl
import com.nvshink.domain.exercise.repository.ExerciseRepository
import com.nvshink.domain.localstorage.LocalStorage
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
    abstract fun bindExerciseRepository(exerciseRepositoryImpl: ExerciseRepositoryImpl): ExerciseRepository

    @Binds
    @Singleton
    abstract fun bindTrainingPlanRepository(trainingPlanRepositoryImpl: TrainingPlanRepositoryImpl): TrainingPlanRepository

    @Binds
    @Singleton
    abstract fun bindTrainingPlanExerciseRepository(trainingPlanExerciseRepositoryImpl: TrainingPlanExerciseRepositoryImpl): TrainingPlanExerciseRepository

    @Binds
    @Singleton
    abstract fun bindLocalStorage(localStorageImpl: LocalStorageImpl): LocalStorage
}