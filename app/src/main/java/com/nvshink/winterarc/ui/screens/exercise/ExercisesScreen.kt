package com.nvshink.winterarc.ui.screens.exercise

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import coil.compose.AsyncImage
import coil.request.ImageRequest
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
                        onEditButtonClick = {
                            onEvent(ExerciseEvent.ShowDialog)
                        },
                        onDeleteButtonClick = {
                            onEvent(ExerciseEvent.DeleteExercise(exercise))
                        },
                        onBackPressed = {
                            navController.navigate(EmptyItemScreen)
                            onEvent(ExerciseEvent.ShowList)
                        }
                    )
                }
            }
        }
    }
    if (exerciseUiState.isAddingDialog) ExerciseEditDialog(
        exerciseUiState = exerciseUiState,
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
    exerciseUiState: ExerciseUiState,
    onEvent: (ExerciseEvent) -> Unit
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> imageUri = uri }
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
                Row {
                    IconButton(onClick = {
                        onEvent(ExerciseEvent.HideDialog)
                    }) {
                        Icon(Icons.Filled.Close, contentDescription = "Close button")
                    }
                    Text(text = "Add exercise", style = MaterialTheme.typography.titleLarge)
                    TextButton(onClick = {
                        onEvent(ExerciseEvent.SaveExercise)
                        onEvent(ExerciseEvent.HideDialog)
                    }) {
                        Text("Save")
                    }
                }
                TextField(
                    value = exerciseUiState.name,
                    label = {
                        Text("name")
                    }, onValueChange = { it: String ->
                        onEvent(ExerciseEvent.SetName(it))
                    })
                TextField(
                    value = exerciseUiState.description,
                    label = {
                        Text("description")
                    }, onValueChange = { it: String ->
                        onEvent(ExerciseEvent.SetDescription(it))
                    })
                Button(onClick = {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }) {
                    Text(text = "Pick Photo")
                }
                imageUri?.let { uri ->
                    AsyncImage(
                        model = ImageRequest.Builder(context).data(uri).build(),
                        contentDescription = "Selected Image",
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        }
    }
}

fun saveImageToLocalStorage(context: Context, uri: Uri): Uri? {
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