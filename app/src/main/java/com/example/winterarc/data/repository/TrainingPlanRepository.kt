package com.example.winterarc.data.repository

import com.example.winterarc.data.Datasource
import com.example.winterarc.data.model.Exercise
import com.example.winterarc.data.model.TrainingPlan

class TrainingPlanRepository {
    private val trainingPlans = Datasource.loadTrainingPlan()

    fun getTrainingPlans(): List<TrainingPlan> {
        return trainingPlans
    }
}