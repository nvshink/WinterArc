package com.nvshink.data.local.trainingplan.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.nvshink.data.local.trainingplan.entity.TrainingPlanEntity
import com.nvshink.data.local.trainingplanexercise.entity.TrainingPlanExerciseEntity

data class TrainingPlanWithTrainingPlanExercises(
    @Embedded val trainingPlan: TrainingPlanEntity,
    @Relation(
        parentColumn = "training_plan_id",
        entityColumn  = "training_plan_exercise_id"
    )
    val trainingPlanExercises: List<TrainingPlanExerciseEntity>
)