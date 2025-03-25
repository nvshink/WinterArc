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
    @StringRes val contentDescription: Int
) {
    TRAINING_PLAN(R.string.training_plans_nav_label, Icons.Default.FormatListNumbered, R.string.training_plans_nav_content_description),
    EXERCISES(R.string.exercises_nav_label, Icons.Default.FitnessCenter, R.string.exercises_nav_content_description),
    PROFILE(R.string.profile_nav_label, Icons.Default.Person, R.string.profile_nav_content_description),
}