package com.nvshink.winterarc.data.repository

import com.nvshink.winterarc.data.Datasource
import com.nvshink.winterarc.data.model.Exercise

class ExerciseRepository {
    private val exercises = Datasource.loadExercises()

    fun getExercises(): MutableMap<Int, Exercise> {
        val exercisesMap: MutableMap<Int, Exercise> = mutableMapOf()
        exercises.forEach { exercise ->
            exercisesMap[exercise.id] = exercise
        }
        return exercisesMap
    }
}