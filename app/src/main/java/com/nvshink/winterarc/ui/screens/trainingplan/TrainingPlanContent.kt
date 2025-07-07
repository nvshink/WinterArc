package com.nvshink.winterarc.ui.screens.trainingplan

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
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
import com.nvshink.domain.trainingplan.model.TrainingPlanModel
import com.nvshink.winterarc.ui.components.generic.WinterArcItemScreenTopBar
import com.nvshink.winterarc.ui.components.generic.WinterArcListItem
import com.nvshink.winterarc.ui.states.TrainingPlanExerciseUiState
import com.nvshink.winterarc.ui.states.TrainingPlanUiState

@Composable
fun WinterArcTrainingPlanItemScreen(
    modifier: Modifier = Modifier,
    onExercisePressed: (Long) -> Unit,
    trainingPlan: TrainingPlanModel,
    trainingPlanUiState: TrainingPlanUiState,
    trainingPlanExercisesUiState: TrainingPlanExerciseUiState,
    onEditButtonClick: (() -> Unit)? = null,
    onDeleteButtonClick: (() -> Unit)? = null,
    onBackPressed: () -> Unit,
) {

    BackHandler {
        onBackPressed()
    }
    Column(modifier = modifier.fillMaxSize()) {
        WinterArcItemScreenTopBar(modifier = Modifier.padding(16.dp), onBackButtonClicked = onBackPressed, isBigScreen = trainingPlanUiState.isBigScreen) {
            if (onEditButtonClick != null) {
                IconButton(onClick = onEditButtonClick) {
                    Icon(Icons.Filled.Edit, contentDescription = "")
                }
            }
            if (onDeleteButtonClick != null) {
                IconButton(onClick = onDeleteButtonClick) {
                    Icon(Icons.Filled.Delete, contentDescription = "")
                }
            }
        }
        if (trainingPlanUiState.currentTrainingPlan != null) {
            if(trainingPlanExercisesUiState is TrainingPlanExerciseUiState.SuccessState){
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    item {
                        Text(
                            text = trainingPlan.name,
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(bottom = 20.dp)
                        )
                        Text(
                            text = trainingPlan.name,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 15.dp)
                        )
                    }
//                    items(trainingPlanExercisesUiState.pairExerciseAndParams) { trainingPlanExercise ->
//                        val exercise: Exercise = trainingPlanExercise.first
//                        val trainingPlanExerciseParams: TrainingPlanExercise.TrainingPlanExerciseParams =
//                            trainingPlanExercise.second
//                        WinterArcListItem(
//                            modifier = Modifier.padding(bottom = 5.dp),
//                            title = exercise.name,
//                            subtitle = exercise.description,
//                            onCardClick = { onExercisePressed(exercise.id) },
//                        ) {
//                            Column(
//                                modifier = Modifier.width(70.dp),
//                                horizontalAlignment = Alignment.CenterHorizontally
//                            ) {
//                                Icon(
//                                    if (trainingPlanExerciseParams.isInSets) Icons.Filled.Repeat else Icons.Filled.HourglassEmpty,
//                                    contentDescription = "",
//                                    modifier = Modifier.size(16.dp)
//                                )
//                                Text(
//                                    text = if (trainingPlanExerciseParams.isInSets) "x${trainingPlanExerciseParams.duration}" else "${trainingPlanExerciseParams.duration} c",
//                                    style = MaterialTheme.typography.bodyMedium
//                                )
//                            }
//                        }
//                    }
                }
            }
        } else {
            Text(text = "No selected")

        }
    }
}

