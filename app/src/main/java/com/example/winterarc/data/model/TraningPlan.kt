package com.example.winterarc.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TrainingPlan (val id: Int, var name: String, var description: String, var exercises: List<Triple<Exercise, Int, Boolean>>)