package com.example.winterarc.data

import com.example.winterarc.data.model.Exercise
import com.example.winterarc.data.model.TrainingPlan
import com.example.winterarc.data.model.User

//import com.example.affirmations.R
//import com.example.affirmations.model.Affirmation

/**
 * [Datasource] generates a list of [Affirmation]
 */
object Datasource {
    val loremIpsum: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent facilisis, felis sed rutrum pretium, arcu metus aliquet justo, ac bibendum elit felis eget nisl. Aenean nec sem vitae lorem condimentum euismod ut nec ante. In vitae felis scelerisque, varius nunc nec, venenatis felis. Suspendisse augue nisi, iaculis vel lorem sit amet, porta ullamcorper diam. Etiam fringilla ac arcu nec iaculis. Donec scelerisque in est in cursus. Mauris volutpat eros ac mi pretium, non convallis ante placerat. In massa nunc, elementum dapibus ultrices a, condimentum et tortor. Nulla tempor convallis turpis ut sagittis."
    fun loadExercises(): List<Exercise> {
        return listOf<Exercise>(
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
    }



    private val backListExercises: List<Exercise> = loadExercises().subList(0, 4)
    private val backListExercisesTrainingPlan: List<Triple<Exercise, Int, Boolean>> = listOf(
        Triple(
            backListExercises[0], 10, true
        ),
        Triple(
            backListExercises[1], 20, true
        ),
        Triple(
            backListExercises[2], 10, true
        ),
        Triple(
            backListExercises[3], 5, true
        )
    )
    private val legListExercises: List<Exercise> = loadExercises().subList(4, 6)
    private val legListExercisesTrainingPlan: List<Triple<Exercise, Int, Boolean>> = listOf(
        Triple(
            legListExercises[0], 300, false
        ),
        Triple(
            legListExercises[1], 20, true
        )
    )
    private val armListExercises: List<Exercise> = loadExercises().subList(6, 9)
    private val armListExercisesTrainingPlan: List<Triple<Exercise, Int, Boolean>> = listOf(
        Triple(
            armListExercises[0], 10, true
        ),
        Triple(
            armListExercises[1], 20, true
        ),
        Triple(
            armListExercises[2], 100, false
        )
    )
    fun loadTrainingPlan(): List<TrainingPlan> {
        return listOf<TrainingPlan>(
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