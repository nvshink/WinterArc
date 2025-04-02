package com.nvshink.winterarc.ui.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

object WinterArcDestinations {
    data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)

    private val topLevelRoutes = listOf(
        TopLevelRoute("Training plan", TrainingPlanScreenRoute, Icons.Filled.FormatListNumbered),
        TopLevelRoute("Exercise", ExerciseScreenRoute, Icons.Filled.FitnessCenter),
        TopLevelRoute("Profile", ProfileScreenRoute, Icons.Filled.Person)
    )

    fun getTopLevelRoutes(): List<TopLevelRoute<out Any>> {
        return topLevelRoutes
    }

    fun getDefaultTopLevelRoute(): TopLevelRoute<out Any> {
        val defaultTopLevelRoute: TopLevelRoute<out Any> = topLevelRoutes.get(0)
        return defaultTopLevelRoute
    }
}

@Serializable
object ExerciseScreenRoute

@Serializable
object TrainingPlanScreenRoute

@Serializable
object ProfileScreenRoute

@Serializable
object EmptyItemScreen

@Serializable
data class ExerciseItemScreen(
    val id: Int
)

@Serializable
data class TrainingPlanItemScreen(
    val id: Int
)