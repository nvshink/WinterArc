package com.example.winterarc.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.winterarc.data.model.Exercise
import com.example.winterarc.data.model.TrainingPlan

@Composable
fun WinterArcTrainingPlanItemScreen(
    modifier: Modifier = Modifier,
    onExercisePressed: (Int) -> Unit,
    onBackPressed: () -> Unit,
    trainingPlan: TrainingPlan?,
) {
    BackHandler {
        onBackPressed()
    }
    Column(modifier = modifier.fillMaxSize()) {
        WinterArcItemScreenTopBar(onBackButtonClicked = onBackPressed) {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Edit, contentDescription = "")
            }
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Delete, contentDescription = "")
            }
        }
        if (trainingPlan == null) {
            Text(text = "No selected")
        } else {
            LazyColumn(modifier = modifier
                .fillMaxSize()
                .padding(20.dp)) {
                item {
                    Text(
                        text = trainingPlan.name,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )
                    Text(
                        text = trainingPlan.description,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(bottom = 15.dp)
                    )
                }
                items(trainingPlan.exercises) { trainingPlanExercise ->
                    val exercise: Exercise = trainingPlanExercise.first
                    WinterArcListItem(
                        modifier = Modifier.padding(bottom = 5.dp),
                        title = exercise.name,
                        subtitle = exercise.description,
                        onCardClick = { onExercisePressed(trainingPlanExercise.first.id) },
                    ) {
                        Column(
                            modifier = Modifier.width(70.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                if (trainingPlanExercise.third) Icons.Filled.Repeat else Icons.Filled.HourglassEmpty,
                                contentDescription = "",
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = if (trainingPlanExercise.third) "x${trainingPlanExercise.second}" else "${trainingPlanExercise.second} c",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

