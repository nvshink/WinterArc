package com.example.winterarc.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.winterarc.data.model.Exercise

@Composable
fun ListOnlyContent(
    modifier: Modifier = Modifier,
    exerciseUiState: ExerciseUiState,
    onExerciseCardPressed: (Exercise) -> Unit
) {
    ExercisesListItems(exerciseUiState.exercisesList, onExerciseCardPressed)
}

@Composable
fun ExercisesListItems(
    exercises: List<Exercise>,
    onExerciseCardPressed: (Exercise) -> Unit
) {
    LazyColumn {
        items(exercises) { exercise ->
            WinterArcListItem(
                title = exercise.name,
                subtitle = exercise.description,
                onCardClick = { onExerciseCardPressed(exercise) }
            )
        }
    }
}