package com.example.winterarc.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.winterarc.data.model.TrainingPlan

@Composable
fun TrainingPlansListOnlyContent(
    modifier: Modifier = Modifier,
    trainingPlanUiState: TrainingPlanUiState,
    onTrainingPlanCardPressed: (TrainingPlan) -> Unit,
    onTrainingPlanItemScreenBackPressed: () -> Unit
) {
    if (trainingPlanUiState.isShowingList) {
        TrainingPlansListItems(
            trainingPlans = trainingPlanUiState.trainingPlanList,
            onTrainingPlanCardPressed = onTrainingPlanCardPressed
        )
    } else {
        TrainingPlanItemScreen(
            modifier = Modifier.fillMaxSize(),
            onBackPressed = onTrainingPlanItemScreenBackPressed,
            trainingPlan = trainingPlanUiState.currentTrainingPlan
        )
    }
}

@Composable
fun TrainingPlansListAndDetailContent(
    modifier: Modifier = Modifier,
    trainingPlanUiState: TrainingPlanUiState,
    onTrainingPlanCardPressed: (TrainingPlan) -> Unit,
    onTrainingPlanItemScreenBackPressed: () -> Unit
) {
    Row(modifier = modifier) {
        TrainingPlansListItems(modifier = Modifier.weight(1f), trainingPlanUiState.trainingPlanList, onTrainingPlanCardPressed)
        TrainingPlanItemScreen(
            modifier = Modifier.weight(1f),
            onBackPressed = onTrainingPlanItemScreenBackPressed,
            trainingPlan = trainingPlanUiState.currentTrainingPlan
        )
    }
}

@Composable
fun TrainingPlansListItems(
    modifier: Modifier = Modifier,
    trainingPlans: List<TrainingPlan>,
    onTrainingPlanCardPressed: (TrainingPlan) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(trainingPlans) { trainingPlan ->
            WinterArcListItem(
                title = trainingPlan.name,
                subtitle = trainingPlan.description,
                onCardClick = { onTrainingPlanCardPressed(trainingPlan) }
            )
        }
    }
}

@Composable
fun TrainingPlanItemScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    trainingPlan: TrainingPlan?,
) {
    BackHandler {
        onBackPressed()
    }
    Column(modifier = modifier.fillMaxSize()) {
        if (trainingPlan == null) {
            Text(text = "No selected")
        } else {
            Column {
                Text(text = trainingPlan.name, style = MaterialTheme.typography.titleLarge)
                Text(text = trainingPlan.name, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

//@Composable
//fun