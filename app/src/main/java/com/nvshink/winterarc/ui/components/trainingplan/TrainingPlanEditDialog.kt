package com.nvshink.winterarc.ui.components.trainingplan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nvshink.winterarc.R
import com.nvshink.winterarc.ui.components.generic.WinterArcDialog
import com.nvshink.winterarc.ui.event.TrainingPlanEvent
import com.nvshink.winterarc.ui.event.TrainingPlanExercisesEvent
import com.nvshink.winterarc.ui.states.TrainingPlanExerciseUiState
import com.nvshink.winterarc.ui.viewModels.ExerciseViewModel
import com.nvshink.winterarc.ui.states.TrainingPlanUiState
import com.nvshink.winterarc.ui.utils.WinterArcContentType
import com.nvshink.winterarc.ui.viewModels.TrainingPlanExerciseViewModel

@Composable
fun TrainingPlanEditDialog(
    modifier: Modifier = Modifier,
    title: String,
    trainingPlanUiState: TrainingPlanUiState.SuccessState,
    trainingPlanExerciseUiState: TrainingPlanExerciseUiState,
    contentType: WinterArcContentType,
    onTrainingPlanEvent: (TrainingPlanEvent) -> Unit,
    onTrainingPlanExercisesEvent: (TrainingPlanExercisesEvent) -> Unit,
) {
    val exerciseViewModel: ExerciseViewModel = hiltViewModel()
    val exerciseUiState = exerciseViewModel.uiState.collectAsState().value

    if (trainingPlanUiState.isShowingExerciseSelector && trainingPlanExerciseUiState is TrainingPlanExerciseUiState.SuccessState) {
        ExerciseSelectorDialog(
            trainingPlanUiState = trainingPlanUiState,
            exerciseUiState = exerciseUiState,
            contentType = contentType,
            onExerciseClick = {
                onTrainingPlanExercisesEvent(TrainingPlanExercisesEvent.AddTrainingPlanExercise(insertedIndex = trainingPlanExerciseUiState.insertedIndexExercise, it))
                onTrainingPlanEvent(TrainingPlanEvent.HideExerciseSelector)
            },
            onBack = {
                onTrainingPlanEvent(TrainingPlanEvent.HideDialog)
            },
        )
    }
    WinterArcDialog(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    onTrainingPlanEvent(TrainingPlanEvent.HideDialog)
                }) {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = stringResource(R.string.close_button_icon_description),
                        modifier = Modifier.size(24.dp)
                    )
                }
                Text(
                    text = title,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                TextButton(onClick = {
                    onTrainingPlanEvent(TrainingPlanEvent.SaveTrainingPlan)
                    onTrainingPlanEvent(TrainingPlanEvent.HideDialog)
                }) {
                    Text(
                        stringResource(R.string.save_button_name),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        content = {
            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = trainingPlanUiState.name,
                    label = {
                        Text(stringResource(R.string.text_field_label_name))
                    },
                    placeholder = {
                        Text(stringResource(R.string.text_field_placeholder_name))
                    },
                    onValueChange = { it: String ->
                        onTrainingPlanEvent(TrainingPlanEvent.SetName(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = trainingPlanUiState.description,
                    label = {
                        Text(stringResource(R.string.text_field_label_description))
                    },
                    placeholder = {
                        Text(stringResource(R.string.text_field_placeholder_description))
                    },
                    onValueChange = { it: String ->
                        onTrainingPlanEvent(TrainingPlanEvent.SetDescription(it))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
                LazyColumn {
                    itemsIndexed(trainingPlanExerciseUiState.pairExerciseAndParams) { index, it ->
                        val exercise = it.first
                        val params = it.second
                        Card {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(exercise.name)
                                Text(params.duration.toString())
                                IconButton(onClick = {
                                    onTrainingPlanExercisesEvent(TrainingPlanExercisesEvent.DeleteTrainingPlanExercise(index))
                                }) { Icon(Icons.Filled.Close, contentDescription = "") }
                            }
                        }
                        if(index < trainingPlanExerciseUiState.pairExerciseAndParams.lastIndex){
                            Card(
                                onClick = {
                                    onTrainingPlanExercisesEvent(
                                        TrainingPlanExercisesEvent.SetInsertedIndexExercise(index + 1)
                                    )
                                    onTrainingPlanEvent(
                                        TrainingPlanEvent.ShowExerciseSelector
                                    )

                                },
                                modifier = Modifier
                                    .height(40.dp)
                                    .fillParentMaxWidth()
                                    .padding(5.dp)
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        Icons.Filled.Add,
                                        contentDescription = "stringResource", //TODO()
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                    item {
                        Card(
                            onClick = {
                                onTrainingPlanEvent(
                                    TrainingPlanEvent.ShowExerciseSelector
                                )
                            },
                            modifier = Modifier
                                .size(100.dp)
                                .padding(5.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Filled.Add,
                                    contentDescription = "stringResource", //TODO()
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                        }
                    }
                }
            }
        },
        contentType = contentType,
        onDismissRequest = {
            onTrainingPlanEvent(TrainingPlanEvent.HideDialog)
        }
    )
}

