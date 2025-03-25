package com.example.winterarc.data.model

data class TrainingPlan (var name: String, var description: String, var exercises: List<Triple<Exercise, Int, Boolean>>)