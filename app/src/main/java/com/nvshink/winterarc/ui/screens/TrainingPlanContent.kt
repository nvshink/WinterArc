package com.nvshink.winterarc.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nvshink.winterarc.data.model.TrainingPlan
import com.nvshink.winterarc.ui.components.generic.WinterArcItemScreenTopBar
import com.nvshink.winterarc.ui.viewModel.TrainingPlanUiState

@Composable
fun WinterArcTrainingPlanItemScreen(
    modifier: Modifier = Modifier,
    onExercisePressed: (Int) -> Unit,
    onBackPressed: () -> Unit,
    trainingPlanUiState: TrainingPlanUiState,
) {
    BackHandler {
        onBackPressed()
    }
    Column(modifier = modifier.fillMaxSize()) {
        WinterArcItemScreenTopBar(onBackButtonClicked = onBackPressed, isBigScreen = trainingPlanUiState.isBigScreen) {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Edit, contentDescription = "")
            }
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Delete, contentDescription = "")
            }
        }
        if (trainingPlanUiState.currentTrainingPlan == null) {
            Text(text = "No selected")
        } else {
            LazyColumn(modifier = modifier
                .fillMaxSize()
                .padding(20.dp)) {
                item {
                    Text(
                        text = trainingPlanUiState.currentTrainingPlan.name,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                    Text(
                        text = trainingPlanUiState.currentTrainingPlan.description,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 15.dp)
                    )
                }
//                items(trainingPlan.exercises) { trainingPlanExercise ->
//                    val exercise: Exercise = trainingPlanExercise.exercise
//                    WinterArcListItem(
//                        modifier = Modifier.padding(bottom = 5.dp),
//                        title = exercise.name,
//                        subtitle = exercise.description,
//                        onCardClick = { onExercisePressed(exercise.id) },
//                    ) {
//                        Column(
//                            modifier = Modifier.width(70.dp),
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                            Icon(
//                                if (trainingPlanExercise.isInSets) Icons.Filled.Repeat else Icons.Filled.HourglassEmpty,
//                                contentDescription = "",
//                                modifier = Modifier.size(16.dp)
//                            )
//                            Text(
//                                text = if (trainingPlanExercise.isInSets) "x${trainingPlanExercise.duration}" else "${trainingPlanExercise.duration} c",
//                                style = MaterialTheme.typography.bodyMedium
//                            )
//                        }
//                    }
//                }
            }
        }
    }
}

