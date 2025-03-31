package com.example.winterarc.data.repository

import com.example.winterarc.data.Datasource
import com.example.winterarc.data.model.Exercise
import com.example.winterarc.data.model.TrainingPlan

class ExerciseRepository {
    private val exercises = Datasource.loadExercises()

    fun getExercises(): MutableMap<Int, Exercise> {
        val exercisesMap: MutableMap<Int, Exercise> = mutableMapOf()
        exercises.forEach { exercise ->
            exercisesMap.put(exercise.id, exercise)
        }
        return exercisesMap
    }
}