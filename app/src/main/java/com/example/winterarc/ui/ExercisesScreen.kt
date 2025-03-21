package com.example.winterarc.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.winterarc.data.model.Exercise
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ExercisesScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: ExerciseViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    ExercisesListItem(
        exercises = uiState.exercisesList
    )
}

@Composable
fun ExercisesListItem (
    exercises: List<Exercise>
) {
    LazyColumn {
        items(exercises) { exercise ->
            ExerciseListItem(
                exercise = exercise
            )
        }
    }
}

@Composable
fun ExerciseListItem (
    exercise: Exercise
) {
    Card (modifier = Modifier.padding(100.dp), elevation = CardDefaults.cardElevation(1.dp)) {
        Row {
            Text(text = exercise.name)
                Icon(imageVector = Icons.Default.Info, contentDescription = null)
        }
    }
}