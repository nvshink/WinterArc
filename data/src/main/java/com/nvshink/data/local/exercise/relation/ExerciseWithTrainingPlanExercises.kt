package com.nvshink.data.local.exercise.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.nvshink.data.local.exercise.entity.ExerciseEntity
import com.nvshink.data.local.trainingplanexercise.entity.TrainingPlanExerciseEntity
import com.nvshink.domain.trainingplanexercise.model.TrainingPlanExerciseModel

data class ExerciseWithTrainingPlanExercises(
    @Embedded val exercise: ExerciseEntity,
    @Relation(
        parentColumn = "exercise_id",
        entityColumn = "training_plan_exercise_id"
    )
    val trainingPlanExercises: List<TrainingPlanExerciseEntity>
)