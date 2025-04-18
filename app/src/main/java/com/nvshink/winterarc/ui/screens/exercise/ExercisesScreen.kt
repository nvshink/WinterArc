package com.nvshink.winterarc.ui.screens.exercise

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import coil.compose.AsyncImage
import com.nvshink.winterarc.R
import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.ui.components.WinterArcItemDetail
import com.nvshink.winterarc.ui.components.WinterArcListDetailRoute
import com.nvshink.winterarc.ui.components.WinterArcListItem
import com.nvshink.winterarc.ui.event.ExerciseEvent
import com.nvshink.winterarc.ui.screens.WinterArcEmptyItemScreen
import com.nvshink.winterarc.ui.utils.ExerciseItemScreen
import com.nvshink.winterarc.ui.utils.EmptyItemScreen
import com.nvshink.winterarc.ui.utils.WinterArcContentType
import com.nvshink.winterarc.ui.viewModel.ExerciseUiState
import java.io.File

@Composable
fun ExercisesScreen(
    modifier: Modifier = Modifier,
    exerciseUiState: ExerciseUiState,
    contentType: WinterArcContentType,
    onEvent: (ExerciseEvent) -> Unit
) {
    val navController = rememberNavController()
    val navHost = movableContentOf<PaddingValues> {
        NavHost(navController = navController, startDestination = EmptyItemScreen) {
            composable<EmptyItemScreen> {
                WinterArcEmptyItemScreen()
            }
            composable<ExerciseItemScreen> {
                val args = it.toRoute<ExerciseItemScreen>()
                val exercise: Exercise? = exerciseUiState.exercisesMap[args.id]
                if (exercise != null) {
                    onEvent(ExerciseEvent.UpdateCurrentExercise(exercise))
                    onEvent(ExerciseEvent.HideList)
                    WinterArcExerciseItemScreen(
                        exercise = exercise,
                        exerciseUiState = exerciseUiState,
                        onEvent = onEvent,
                        onEditButtonClick = {
                            onEvent(ExerciseEvent.SetName(exercise.name))
                            onEvent(ExerciseEvent.SetDescription(exercise.description))
                            onEvent(ExerciseEvent.SetImages(exercise.images))
                            onEvent(ExerciseEvent.ShowDialog)
                        },
                        onDeleteButtonClick = {
                            onEvent(ExerciseEvent.DeleteExercise(exercise))
                            onEvent(ExerciseEvent.HideDialog)
                        },
                        onBackPressed = {
                            onEvent(ExerciseEvent.ShowList)
                            navController.navigate(EmptyItemScreen)
                            onEvent(ExerciseEvent.UpdateCurrentExercise(null))
                        }
                    )
                }
            }
        }
    }
    if (exerciseUiState.isAddingDialog) ExerciseEditDialog(
        exerciseUiState = exerciseUiState,
        title = stringResource(R.string.add_exercise_dialog_title),
        onEvent = onEvent
    )
    WinterArcListDetailRoute(
        modifier = modifier,
        contentType = contentType,
        isShowingList = exerciseUiState.isShowingList,
        listOfItems = exerciseUiState.exercisesMap,
        listArrangement = 10.dp,
        onEvent = onEvent,
        details = {
            WinterArcItemDetail {
                navHost(PaddingValues())
            }
        },
        listItem = { item ->
            WinterArcListItem(
                title = item.name,
                subtitle = null,
                additionalInfo = null,
                onCardClick = {
                    navController.navigate(route = ExerciseItemScreen(item.id))
                },
            )
        },
        fab = {
            FloatingActionButton({
                onEvent(ExerciseEvent.ShowDialog)
            }, modifier = it) {
                Icon(Icons.Filled.Add, contentDescription = "")
            }
        }
    )
}

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
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        onEvent(ExerciseEvent.HideDialog)
                    }) {
                        Icon(
                            Icons.Filled.Close,
                            contentDescription = stringResource(R.string.close_button_icon_description)
                        )
                    }
                    Text(text = title, style = MaterialTheme.typography.titleLarge)
                    TextButton(onClick = {
                        onEvent(ExerciseEvent.SetImages(
                            exerciseUiState.images.map {
                                if (exerciseUiState.currentExercise?.images?.contains(it)?.not()
                                        ?: true
                                ) {
                                    return@map saveImageToLocalStorage(
                                        context = context,
                                        uri = Uri.parse(it)
                                    ).toString()
                                } else return@map it
                            }
                        ))
                        exerciseUiState.currentExercise?.images?.forEach {
                            if (exerciseUiState.images.contains(it).not()) {
                                deleteImageFromLocalStorage(uri = Uri.parse(it))
                            }
                        }
                        onEvent(ExerciseEvent.SaveExercise)
                        onEvent(ExerciseEvent.HideDialog)
                    }) {
                        Text(stringResource(R.string.save_button_name))
                    }
                }
                Column(modifier = Modifier.padding(horizontal = 20.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(30.dp)) {
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

private fun saveImageToLocalStorage(context: Context, uri: Uri): Uri? {
    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(uri)
    val fileName = "saved_image_${System.currentTimeMillis()}.jpg"
    val outputFile = File(context.filesDir, fileName)

    inputStream?.use { input ->
        outputFile.outputStream().use { output ->
            input.copyTo(output)
        }
    }

    return Uri.fromFile(outputFile)
}

private fun deleteImageFromLocalStorage(uri: Uri): Boolean {
    val file = File(uri.path ?: return false)
    return file.delete()
}