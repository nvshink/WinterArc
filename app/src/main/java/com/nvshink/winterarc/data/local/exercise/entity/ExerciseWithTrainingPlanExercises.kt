package com.nvshink.winterarc.data.local.exercise.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.nvshink.winterarc.data.local.trainingplanexercise.entity.TrainingPlanExercise

data class ExerciseWithTrainingPlanExercises(
    @Embedded val exercise: ExerciseEntity,
    @Relation(
        parentColumn = "exercise_id",
        entityColumn = "training_plan_exercise_id"
    )
    val trainingPlanExercises: List<TrainingPlanExercise>
)