package com.example.winterarc.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.winterarc.data.model.Exercise

@Composable
fun ExerciseListOnlyContent(
    modifier: Modifier = Modifier,
    exerciseUiState: ExerciseUiState,
    onExerciseCardPressed: (Exercise) -> Unit,
    onExerciseItemScreenBackPressed: () -> Unit
) {
    if (exerciseUiState.isShowingList) {
        ExercisesListItems(
            exercises = exerciseUiState.exercisesList,
            onExerciseCardPressed = onExerciseCardPressed
        )
    } else {
        ExerciseItemScreen(
            modifier = Modifier.fillMaxSize(),
            onBackPressed = onExerciseItemScreenBackPressed,
            exercise = exerciseUiState.currentExercise
        )
    }
}

@Composable
fun ExerciseListAndDetailContent(
    modifier: Modifier = Modifier,
    exerciseUiState: ExerciseUiState,
    onExerciseCardPressed: (Exercise) -> Unit,
    onExerciseItemScreenBackPressed: () -> Unit
) {
    Row(modifier = modifier) {
        ExercisesListItems(modifier = Modifier.weight(1f), exerciseUiState.exercisesList, onExerciseCardPressed)
        ExerciseItemScreen(
            modifier = Modifier.weight(1f),
            onBackPressed = onExerciseItemScreenBackPressed,
            exercise = exerciseUiState.currentExercise
        )
    }
}

@Composable
fun ExercisesListItems(
    modifier: Modifier = Modifier,
    exercises: List<Exercise>,
    onExerciseCardPressed: (Exercise) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(exercises) { exercise ->
            WinterArcListItem(
                title = exercise.name,
                subtitle = exercise.description,
                onCardClick = { onExerciseCardPressed(exercise) }
            )
        }
    }
}

@Composable
fun ExerciseItemScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    exercise: Exercise?,
) {
    BackHandler {
        onBackPressed()
    }
    Column(modifier = modifier.fillMaxSize()) {
        if (exercise == null) {
            Text(text = "No selected")
        } else {
            Text(text = exercise.name)
        }
    }
}