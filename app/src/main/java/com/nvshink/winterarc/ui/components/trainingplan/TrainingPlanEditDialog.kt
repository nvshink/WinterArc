package com.nvshink.winterarc.ui.components.trainingplan

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.nvshink.winterarc.R
import com.nvshink.winterarc.ui.components.generic.WinterArcListItem
import com.nvshink.winterarc.ui.components.generic.WinterArcListOfItems
import com.nvshink.winterarc.ui.event.TrainingPlanEvent
import com.nvshink.winterarc.ui.states.ExerciseUiState
import com.nvshink.winterarc.ui.viewModels.ExerciseViewModel
import com.nvshink.winterarc.ui.states.TrainingPlanUiState

@Composable
fun TrainingPlanEditDialog(
    modifier: Modifier = Modifier,
    title: String,
    trainingPlanUiState: TrainingPlanUiState.SuccessState,
    onEvent: (TrainingPlanEvent) -> Unit
) {
    val exerciseViewModel: ExerciseViewModel = hiltViewModel()
    val exerciseUiState = exerciseViewModel.uiState.collectAsState().value
    if (trainingPlanUiState.isShowingExerciseSelector) {
        ExerciseSelectorDialog(
            trainingPlanUiState = trainingPlanUiState,
            exerciseUiState = exerciseUiState,
            onEvent = onEvent
        )
    }
    Dialog(
        onDismissRequest = {
            onEvent(TrainingPlanEvent.HideDialog)
        },
        properties = DialogProperties(usePlatformDefaultWidth = trainingPlanUiState.isBigScreen)
    ) {
        Box(
            modifier = modifier
                .clip(
                    if (trainingPlanUiState.isBigScreen) MaterialTheme.shapes.extraLarge else RoundedCornerShape(
                        0.dp
                    )
                )
                .background(MaterialTheme.colorScheme.surface),
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        onEvent(TrainingPlanEvent.HideDialog)
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
                        onEvent(TrainingPlanEvent.SaveTrainingPlan)
                        onEvent(TrainingPlanEvent.HideDialog)
                    }) {
                        Text(
                            stringResource(R.string.save_button_name),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
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
                            onEvent(TrainingPlanEvent.SetName(it))
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
                            onEvent(TrainingPlanEvent.SetDescription(it))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )
                    LazyColumn {
                        itemsIndexed(trainingPlanUiState.trainingPlanExercises) { index, it ->
                            Card {
                                Row {
                                    Text(it.first.name)
                                    Text(it.second.duration.toString())
                                    IconButton(onClick = {
                                        onEvent(
                                            TrainingPlanEvent.SetTrainingPlanExercises(
                                                trainingPlanUiState.trainingPlanExercises.drop(index)
                                            )
                                        )
                                    }) { Icon(Icons.Filled.Close, contentDescription = "") }
                                }
                            }
                        }
                        item {
                            Card(
                                onClick = {
                                    onEvent(
                                        TrainingPlanEvent.ShowExerciseSelector
                                    )
                                    Log.d("SELECTOR", trainingPlanUiState.isShowingExerciseSelector.toString())
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

            }
        }
    }
}

@Composable
fun ExerciseSelectorDialog(
    trainingPlanUiState: TrainingPlanUiState,
    exerciseUiState: ExerciseUiState,
    onEvent: (TrainingPlanEvent) -> Unit
) {
    Dialog(
        onDismissRequest = {
            onEvent(TrainingPlanEvent.HideDialog)
        },
        properties = DialogProperties(usePlatformDefaultWidth = trainingPlanUiState.isBigScreen)
    ) {
        when (exerciseUiState) {
            is ExerciseUiState.SuccessState -> {
                WinterArcListOfItems(
                    listOfItems = exerciseUiState.exercisesMap, listItem = {
                        Row {
                            (
                                    WinterArcListItem(
                                        title = it.name,
                                        subtitle = null,
                                        onCardClick = {
//                                onEvent(TrainingPlanEvent.SetTrainingPlanExercises(trainingPlanUiState.trainingPlanExercises + listOf(Pair(it, ))))
                                        })
                                    )
                        }
                    },
                    listArrangement = 8.dp,
                    isLoading = exerciseUiState::class == ExerciseUiState.LoadingState::class,
                    listTopContent = {},
                    fab = {}
                )
            }

            is ExerciseUiState.LoadingState -> {
                CircularProgressIndicator()
            }

            is ExerciseUiState.ErrorState -> {
                Text("Error")
            }

        }
    }
}