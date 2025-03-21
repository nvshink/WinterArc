package com.example.winterarc.data.repository

import com.example.winterarc.data.Datasource
import com.example.winterarc.data.model.Exercise

class ExerciseRepository {
    private val exercises = Datasource.loadExercises()

    fun getExercises(): List<Exercise> {
        return exercises
    }
}