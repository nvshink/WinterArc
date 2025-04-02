package com.nvshink.winterarc.data

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.data.model.TrainingPlan
import com.nvshink.winterarc.data.model.TrainingPlanExercise
import com.nvshink.winterarc.data.model.User

object Datasource {
    private const val loremIpsum: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent facilisis, felis sed rutrum pretium, arcu metus aliquet justo, ac bibendum elit felis eget nisl. Aenean nec sem vitae lorem condimentum euismod ut nec ante. In vitae felis scelerisque, varius nunc nec, venenatis felis. Suspendisse augue nisi, iaculis vel lorem sit amet, porta ullamcorper diam. Etiam fringilla ac arcu nec iaculis. Donec scelerisque in est in cursus. Mauris volutpat eros ac mi pretium, non convallis ante placerat. In massa nunc, elementum dapibus ultrices a, condimentum et tortor. Nulla tempor convallis turpis ut sagittis."
    fun loadExercises(): List<Exercise> {
        return listOf(
            Exercise(1, "Ажумания", listOf(""), "жум жум"),
            Exercise(2,"Приседат", listOf(""), "жум жум"),
            Exercise(3,"Жым", listOf(""), "жум жум"),
            Exercise(4,"Подтяг", listOf(""), "жум жум"),
            Exercise(5,"Бегит", listOf(""), "жум жум"),
            Exercise(6,"Бицепс", listOf(""), "жум жум"),
            Exercise(7,"Гонтели", listOf(""), "жум жум"),
            Exercise(8,"Прес качат", listOf(""), "жум жум"),
            Exercise(9,"ПланОчка", listOf(""), "жум жум")
        )
        fun loadFromFirestore() {
            val fs = Firebase.firestore
        }
    }



    private val backListExercises: List<Exercise> = loadExercises().subList(0, 4)
    private val backListExercisesTrainingPlan: List<TrainingPlanExercise> = listOf(
        TrainingPlanExercise(
            id = 0,
            exercise = backListExercises[0],
            duration = 10,
            isInSets = true
        ),
        TrainingPlanExercise(
            id = 1,
            exercise = backListExercises[1],
            duration = 20,
            isInSets = true
        ),
        TrainingPlanExercise(
            id = 2,
            exercise = backListExercises[2],
            duration = 10,
            isInSets = true
        ),
        TrainingPlanExercise(
            id = 3,
            exercise = backListExercises[3],
            duration = 15,
            isInSets = true
        )
    )
    private val legListExercises: List<Exercise> = loadExercises().subList(4, 6)
    private val legListExercisesTrainingPlan: List<TrainingPlanExercise> = listOf(
        TrainingPlanExercise(
            id = 0,
            exercise = legListExercises[0],
            duration = 300,
            isInSets = false
        ),
        TrainingPlanExercise(
            id = 1,
            exercise = legListExercises[1],
            duration = 20,
            isInSets = true
        )
    )
    private val armListExercises: List<Exercise> = loadExercises().subList(6, 9)
    private val armListExercisesTrainingPlan: List<TrainingPlanExercise> = listOf(
        TrainingPlanExercise(
            id = 0,
            exercise = armListExercises[0],
            duration = 10,
            isInSets = true
        ),
        TrainingPlanExercise(
            id = 1,
            exercise = armListExercises[1],
            duration = 20,
            isInSets = true
        ),
        TrainingPlanExercise(
            id = 2,
            exercise = armListExercises[0],
            duration = 100,
            isInSets = false
        )
    )
    fun loadTrainingPlan(): List<TrainingPlan> {
        return listOf(
            TrainingPlan(1,"Спина", loremIpsum, backListExercisesTrainingPlan),
            TrainingPlan(2, "Ноги", loremIpsum, legListExercisesTrainingPlan),
            TrainingPlan(3, "Руки", loremIpsum, armListExercisesTrainingPlan)
        )
    }


    private val user = User
    fun loadUser(): User {
        return user
    }
}