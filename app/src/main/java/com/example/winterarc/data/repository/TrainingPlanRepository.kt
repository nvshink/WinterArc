package com.example.winterarc.data.repository

import com.example.winterarc.data.Datasource
import com.example.winterarc.data.model.Exercise
import com.example.winterarc.data.model.TrainingPlan

class TrainingPlanRepository {
    private val trainingPlans = Datasource.loadTrainingPlan()

    fun getTrainingPlans(): MutableMap<Int, TrainingPlan> {
        val trainingPlansMap: MutableMap<Int, TrainingPlan> = mutableMapOf()
        trainingPlans.forEach { trainingPlan ->
            trainingPlansMap.put(trainingPlan.id, trainingPlan)
        }
        return trainingPlansMap
    }
}