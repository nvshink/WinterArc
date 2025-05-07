package com.nvshink.winterarc.ui.components.exercise

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.nvshink.winterarc.R
import com.nvshink.winterarc.ui.event.ExerciseEvent
import com.nvshink.winterarc.ui.states.ExerciseUiState

@Composable
fun ExerciseEditDialog(
    modifier: Modifier = Modifier,
    title: String,
    exerciseUiState: ExerciseUiState,
    onEvent: (ExerciseEvent) -> Unit
) {
    val context = LocalContext.current
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            onEvent(ExerciseEvent.SetImages(exerciseUiState.images + uris.map { it.toString() }))
        }
    )
    Dialog(
        onDismissRequest = {
            onEvent(ExerciseEvent.HideDialog)
        },
        properties = DialogProperties(usePlatformDefaultWidth = exerciseUiState.isBigScreen)
    ) {
        Box(
            modifier = modifier
                .clip(
                    if (exerciseUiState.isBigScreen) MaterialTheme.shapes.extraLarge else RoundedCornerShape(
                        0.dp
                    )
                )
                .background(MaterialTheme.colorScheme.surface),
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        onEvent(ExerciseEvent.HideDialog)
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
                        onEvent(ExerciseEvent.SaveExercise(context = context))
                        onEvent(ExerciseEvent.HideDialog)
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
                        value = exerciseUiState.name,
                        label = {
                            Text(stringResource(R.string.text_field_label_name))
                        },
                        placeholder = {
                            Text(stringResource(R.string.text_field_placeholder_name))
                        },
                        onValueChange = { it: String ->
                            onEvent(ExerciseEvent.SetName(it))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = exerciseUiState.description,
                        label = {
                            Text(stringResource(R.string.text_field_label_description))
                        },
                        placeholder = {
                            Text(stringResource(R.string.text_field_placeholder_description))
                        },
                        onValueChange = { it: String ->
                            onEvent(ExerciseEvent.SetDescription(it))
                        },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )
                    Column(modifier = Modifier.padding(top = 20.dp)) {
                        Text(
                            stringResource(R.string.text_title_selected_examples),
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Start,
                        )
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(minSize = 100.dp),
                        ) {
                            itemsIndexed(exerciseUiState.images) { index, imageUri ->
                                Card(
                                    onClick = {}, modifier = Modifier
                                        .size(100.dp)
                                        .padding(5.dp)
                                ) {
                                    Box {
                                        AsyncImage(
                                            model = imageUri,
                                            contentDescription = null,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                        IconButton(
                                            onClick = {
                                                onEvent(ExerciseEvent.SetImages(exerciseUiState.images.filterIndexed { i, _ -> i != index }))
                                            },
                                            modifier = Modifier
                                                .align(Alignment.TopEnd)
                                        ) {
                                            Icon(Icons.Filled.Close, contentDescription = "")
                                        }
                                    }
                                }
                            }
                            item {
                                Card(
                                    onClick = {
                                        multiplePhotoPickerLauncher.launch(
                                            PickVisualMediaRequest(
                                                ActivityResultContracts.PickVisualMedia.ImageOnly
                                            )
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
                                            contentDescription = stringResource(R.string.add_exercise_image_icon_description),
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
}

