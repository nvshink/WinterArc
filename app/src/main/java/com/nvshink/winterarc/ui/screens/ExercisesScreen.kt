package com.nvshink.winterarc.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.ui.components.WinterArcItemDetail
import com.nvshink.winterarc.ui.components.WinterArcListDetailRoute
import com.nvshink.winterarc.ui.components.WinterArcListItem
import com.nvshink.winterarc.ui.event.ExerciseEvent
import com.nvshink.winterarc.ui.utils.ExerciseItemScreen
import com.nvshink.winterarc.ui.utils.EmptyItemScreen
import com.nvshink.winterarc.ui.utils.WinterArcContentType
import com.nvshink.winterarc.ui.viewModel.ExerciseUiState

@Composable
fun ExercisesScreen(
    modifier: Modifier = Modifier,
    exerciseUiState: ExerciseUiState,
    contentType: WinterArcContentType,
//    onExerciseItemListPressed: (Exercise) -> Unit,
    onEvent: (ExerciseEvent) -> Unit,
    onExerciseItemScreenBackPressed: () -> Unit
) {
    val navController = rememberNavController()
    val navHost = movableContentOf<PaddingValues> {
        NavHost(navController = navController, startDestination = EmptyItemScreen) {
            composable<EmptyItemScreen> {
                WinterArcEmptyItemScreen()
            }
            composable<ExerciseItemScreen> {
                val args = it.toRoute<ExerciseItemScreen>()
                WinterArcExerciseItemScreen(
                    exercise = exerciseUiState.exercisesMap[args.id],
                    exerciseUiState = exerciseUiState,
                    onBackPressed = onExerciseItemScreenBackPressed
                )
            }
        }
    }
    FloatingActionButton(onClick = { onEvent(ExerciseEvent.ShowDialog) }) {
        Icon(Icons.Filled.Add, contentDescription = "Add exercise")
    }
    if(exerciseUiState.isAddingDialog) AddExerciseDialog(exerciseUiState = exerciseUiState, onEvent = onEvent)
    WinterArcListDetailRoute(
        modifier = modifier,
        contentType = contentType,
        isShowingList = exerciseUiState.isShowingList,
        listOfItems = exerciseUiState.exercisesMap,
        listArrangement = 10.dp,
        details = {
            WinterArcItemDetail {
                navHost(PaddingValues())
            }
        },
        onItemScreenBackPressed = onExerciseItemScreenBackPressed,
        listItem = { item ->
            WinterArcListItem(
                title = item.name,
                subtitle = null,
                additionalInfo = null,
                onCardClick = {
                    navController.navigate(route = ExerciseItemScreen(item.id))
                    onEvent(ExerciseEvent.UpdateCurrentExercise(item))
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExerciseDialog(
    modifier: Modifier = Modifier,
    exerciseUiState: ExerciseUiState,
    onEvent: (ExerciseEvent) -> Unit
) {
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
            }
        }
    }
}

