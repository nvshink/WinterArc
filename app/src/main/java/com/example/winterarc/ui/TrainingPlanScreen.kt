package com.example.winterarc.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.winterarc.data.model.TrainingPlan

@Composable
fun TrainingPlanScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: TrainingPlanViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    Box(modifier = modifier) {
        TrainingPlansListItems(
            trainingPlans = uiState.trainingPlanList
        )
    }
}

@Composable
fun TrainingPlansListItems(
    trainingPlans: List<TrainingPlan>
) {
    LazyColumn {
        items(trainingPlans) { trainingPlan ->
            WinterArcListItem(
                title = trainingPlan.name,
                subtitle = trainingPlan.description
            )
        }
    }
}