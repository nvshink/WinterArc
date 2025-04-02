package com.nvshink.winterarc.data.repository

import com.nvshink.winterarc.data.Datasource
import com.nvshink.winterarc.data.model.TrainingPlan

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