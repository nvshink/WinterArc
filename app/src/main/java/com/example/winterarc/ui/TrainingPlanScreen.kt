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
import com.example.winterarc.data.model.Exercise
import com.example.winterarc.data.model.TrainingPlan
import com.example.winterarc.ui.utils.WinterArcContentType

@Composable
fun TrainingPlanScreen(
    modifier: Modifier = Modifier,
    trainingPlanUiState: TrainingPlanUiState,
    contentType: WinterArcContentType,
    onTrainingPlanCardPressed: (TrainingPlan) -> Unit,
    onTrainingPlanItemScreenBackPressed: () -> Unit
) {
    Box(modifier = modifier) {
        if (contentType == WinterArcContentType.LIST_AND_DETAIL) {
            TrainingPlansListAndDetailContent (
                trainingPlanUiState = trainingPlanUiState,
                onTrainingPlanCardPressed = onTrainingPlanCardPressed,
                onTrainingPlanItemScreenBackPressed = onTrainingPlanItemScreenBackPressed
            )
        } else {
            TrainingPlansListOnlyContent (
                trainingPlanUiState = trainingPlanUiState,
                onTrainingPlanCardPressed = onTrainingPlanCardPressed,
                onTrainingPlanItemScreenBackPressed = onTrainingPlanItemScreenBackPressed
            )
        }
    }
}

