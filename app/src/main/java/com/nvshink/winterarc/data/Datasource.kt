package com.nvshink.winterarc.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.data.model.InternalStoragePhoto
import com.nvshink.winterarc.data.model.TrainingPlan
import com.nvshink.winterarc.data.model.TrainingPlanExercise
import com.nvshink.winterarc.data.model.User
import com.nvshink.winterarc.data.repository.ExerciseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException

object Datasource {
    private const val loremIpsum: String =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent facilisis, felis sed rutrum pretium, arcu metus aliquet justo, ac bibendum elit felis eget nisl. Aenean nec sem vitae lorem condimentum euismod ut nec ante. In vitae felis scelerisque, varius nunc nec, venenatis felis. Suspendisse augue nisi, iaculis vel lorem sit amet, porta ullamcorper diam. Etiam fringilla ac arcu nec iaculis. Donec scelerisque in est in cursus. Mauris volutpat eros ac mi pretium, non convallis ante placerat. In massa nunc, elementum dapibus ultrices a, condimentum et tortor. Nulla tempor convallis turpis ut sagittis."

//    fun loadExercises(): List<Exercise> {
//        return listOf(
//            Exercise(
//                id = 1,
//                name = "Ажумания",
//                images = listOf("exerciseImages/images.jpg"),
//                description = "жум жум"
//            ),
//            Exercise(id = 2, name = "Приседат", images = listOf(""), description = "жум жум"),
//            Exercise(id = 3, name = "Жым", images = listOf(""), description = "жум жум"),
//            Exercise(id = 4, name = "Подтяг", images = listOf(""), description = "жум жум"),
//            Exercise(id = 5, name = "Бегит", images = listOf(""), description = "жум жум"),
//            Exercise(id = 6, name = "Бицепс", images = listOf(""), description = "жум жум"),
//            Exercise(id = 7, name = "Гонтели", images = listOf(""), description = "жум жум"),
//            Exercise(id = 8, name = "Прес качат", images = listOf(""), description = "жум жум"),
//            Exercise(id = 9, name = "ПланОчка", images = listOf(""), description = "жум жум")
//        )
//        fun loadFromFirestore() {
//            val fs = Firebase.firestore
//        }
//    }


//    private val backListExercises: List<Exercise> =  ExerciseRepository().getExercises().sub(0, 4)
//    private val backListExercisesTrainingPlan: List<TrainingPlanExercise> = listOf(
//        TrainingPlanExercise(
//            id = 0,
//            exercise = backListExercises[0],
//            duration = 10,
//            isInSets = true
//        ),
//        TrainingPlanExercise(
//            id = 1,
//            exercise = backListExercises[1],
//            duration = 20,
//            isInSets = true
//        ),
//        TrainingPlanExercise(
//            id = 2,
//            exercise = backListExercises[2],
//            duration = 10,
//            isInSets = true
//        ),
//        TrainingPlanExercise(
//            id = 3,
//            exercise = backListExercises[3],
//            duration = 15,
//            isInSets = true
//        )
//    )
//    private val legListExercises: List<Exercise> =  ExerciseRepository().getExercises().subList(4, 6)
//    private val legListExercisesTrainingPlan: List<TrainingPlanExercise> = listOf(
//        TrainingPlanExercise(
//            id = 0,
//            exercise = legListExercises[0],
//            duration = 300,
//            isInSets = false
//        ),
//        TrainingPlanExercise(
//            id = 1,
//            exercise = legListExercises[1],
//            duration = 20,
//            isInSets = true
//        )
//    )
//    private val armListExercises: List<Exercise> =  ExerciseRepository().getExercises().subList(6, 9)
//    private val armListExercisesTrainingPlan: List<TrainingPlanExercise> = listOf(
//        TrainingPlanExercise(
//            id = 0,
//            exercise = armListExercises[0],
//            duration = 10,
//            isInSets = true
//        ),
//        TrainingPlanExercise(
//            id = 1,
//            exercise = armListExercises[1],
//            duration = 20,
//            isInSets = true
//        ),
//        TrainingPlanExercise(
//            id = 2,
//            exercise = armListExercises[0],
//            duration = 100,
//            isInSets = false
//        )
//    )
//
//    fun loadTrainingPlan(): List<TrainingPlan> {
//        return listOf(
//            TrainingPlan(1, "Спина", loremIpsum, backListExercisesTrainingPlan),
//            TrainingPlan(2, "Ноги", loremIpsum, legListExercisesTrainingPlan),
//            TrainingPlan(3, "Руки", loremIpsum, armListExercisesTrainingPlan)
//        )
//    }
//

    private val user = User
    fun loadUser(): User {
        return user
    }


}