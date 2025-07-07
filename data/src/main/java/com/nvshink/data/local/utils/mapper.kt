package com.nvshink.data.local.utils

import com.nvshink.data.local.exercise.entity.ExerciseEntity
import com.nvshink.data.local.trainingplan.entity.TrainingPlanEntity
import com.nvshink.data.local.trainingplanexercise.entity.TrainingPlanExerciseEntity
import com.nvshink.domain.exercise.model.ExerciseModel
import com.nvshink.domain.trainingplan.model.TrainingPlanModel
import com.nvshink.domain.trainingplanexercise.model.TrainingPlanExerciseModel

object ExerciseMapper {

    fun entityToModel(entity: ExerciseEntity): ExerciseModel = ExerciseModel(
        id = entity.id,
        name = entity.name,
        description = entity.description,
        imageLinks = entity.images
    )

    fun modelToEntity(model: ExerciseModel): ExerciseEntity = ExerciseEntity(
        id = model.id,
        name = model.name,
        description = model.description,
        images = model.imageLinks
    )

}

object TrainingPlanMapper {

    fun entityToModel(entity: TrainingPlanEntity): TrainingPlanModel = TrainingPlanModel(
        id = entity.id,
        name = entity.name,
        description = entity.description
    )

    fun modelToEntity(model: TrainingPlanModel): TrainingPlanEntity = TrainingPlanEntity(
        id = model.id,
        name = model.name,
        description = model.description
    )

}

object TrainingPlanExerciseMapper {

    fun entityToModel(entity: TrainingPlanExerciseEntity): TrainingPlanExerciseModel = TrainingPlanExerciseModel(
        id = entity.id,
        isInSets = entity.isInSets,
        duration = entity.duration,
        exerciseId = entity.exerciseIdForeignKey,
        trainingPlanId = entity.trainingPlanIdForeignKey
    )

    fun modelToEntity(model: TrainingPlanExerciseModel): TrainingPlanExerciseEntity = TrainingPlanExerciseEntity(
        id = model.id,
        isInSets = model.isInSets,
        duration = model.duration,
        exerciseIdForeignKey = model.exerciseId ?: 0,
        trainingPlanIdForeignKey = model.trainingPlanId ?: 0
    )

}