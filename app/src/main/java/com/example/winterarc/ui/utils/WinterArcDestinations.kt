package com.example.winterarc.ui.utils

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.winterarc.R

enum class WinterArcDestinations(
    @StringRes val label: Int,
    val icon: ImageVector,
    val route: String
) {
    TRAINING_PLAN(R.string.training_plans_nav_label, Icons.Default.FormatListNumbered, "training-plan"),
    EXERCISES(R.string.exercises_nav_label, Icons.Default.FitnessCenter, "exercises"),
    PROFILE(R.string.profile_nav_label, Icons.Default.Person, "profile")
}
val DEFAULT_DESTINATION = WinterArcDestinations.TRAINING_PLAN