package com.nvshink.winterarc.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.data.model.TrainingPlan
import com.nvshink.winterarc.ui.components.WinterArcItemDetail
import com.nvshink.winterarc.ui.components.WinterArcListDetailRoute
import com.nvshink.winterarc.ui.components.WinterArcListItem
import com.nvshink.winterarc.ui.event.ExerciseEvent
import com.nvshink.winterarc.ui.screens.exercise.WinterArcExerciseItemScreen
import com.nvshink.winterarc.ui.utils.ExerciseItemScreen
import com.nvshink.winterarc.ui.utils.EmptyItemScreen
import com.nvshink.winterarc.ui.utils.TrainingPlanItemScreen
import com.nvshink.winterarc.ui.utils.WinterArcContentType
import com.nvshink.winterarc.ui.viewModel.ExerciseUiState
import com.nvshink.winterarc.ui.viewModel.TrainingPlanUiState

@Composable
fun TrainingPlanScreen(
    modifier: Modifier = Modifier,
    trainingPlanUiState: TrainingPlanUiState,
    exerciseUiState: ExerciseUiState,
    contentType: WinterArcContentType,
    onTrainingPlanItemListPressed: (TrainingPlan) -> Unit,
    onTrainingPlanItemScreenBackPressed: () -> Unit,
    onExerciseEvent: (ExerciseEvent) -> Unit,
) {
    val navController = rememberNavController()
    val navHost = movableContentOf<PaddingValues> {
        NavHost(navController = navController, startDestination = EmptyItemScreen) {
            composable<EmptyItemScreen> {
                WinterArcEmptyItemScreen()
            }
            composable<TrainingPlanItemScreen> {
                val args = it.toRoute<TrainingPlanItemScreen>()
                val selectedTrainingPlan: TrainingPlan? =
                    trainingPlanUiState.trainingPlansMap.get(args.id)
                WinterArcTrainingPlanItemScreen(
                    trainingPlan = selectedTrainingPlan,
                    onExercisePressed = { id ->
                        navController.navigate(route = ExerciseItemScreen(id))
                    },
                    onBackPressed = onTrainingPlanItemScreenBackPressed
                )
            }
            composable<ExerciseItemScreen> {
                val args = it.toRoute<ExerciseItemScreen>()
                val exercise: Exercise? = exerciseUiState.exercisesMap[args.id]
                if (exercise != null) {
                    onExerciseEvent(ExerciseEvent.UpdateCurrentExercise(exercise))
                    WinterArcExerciseItemScreen(
                        exercise = exercise,
                        onEditButtonClick = {
                            onExerciseEvent(ExerciseEvent.ShowDialog)
                        },
                        onDeleteButtonClick = {
                            onExerciseEvent(ExerciseEvent.DeleteExercise(exercise))
                        },
                        onBackPressed = {
                            navController.navigate(EmptyItemScreen)
                        }
                    )
                }
            }
        }
    }
    WinterArcListDetailRoute(
        modifier = modifier.padding(horizontal = 10.dp),
        contentType = contentType,
        isShowingList = trainingPlanUiState.isShowingList,
        listOfItems = trainingPlanUiState.trainingPlansMap,
        listArrangement = 10.dp,
        onEvent = onExerciseEvent,
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
                    onTrainingPlanItemListPressed(item)
                    navController.navigate(route = TrainingPlanItemScreen(item.id))
                }
            )
        }
    )
}

//@Composable
//fun TrainingPlanEditDialog(
//    modifier: Modifier = Modifier,
//    trainingPlanUiState: TrainingPlanUiState,
//    onEvent: () -> Unit
//) {
//    Dialog(
//        onDismissRequest = {
//            onEvent(ExerciseEvent.HideDialog)
//        },
//        properties = DialogProperties(usePlatformDefaultWidth = false)
//    ) {
//        Surface(
//            modifier = modifier,
//            color = MaterialTheme.colorScheme.surface
//        ) {
//            Column(modifier = Modifier.fillMaxSize()) {
//                Row {
//                    IconButton(onClick = {
//                        onEvent(ExerciseEvent.HideDialog)
//                    }) {
//                        Icon(Icons.Filled.Close, contentDescription = "Close button")
//                    }
//                    Text(text = "Add exercise", style = MaterialTheme.typography.titleLarge)
//                    TextButton(onClick = {
//                        onEvent(ExerciseEvent.SaveExercise)
//                        onEvent(ExerciseEvent.HideDialog)
//                    }) {
//                        Text("Save")
//                    }
//                }
//                TextField(
//                    value = exerciseUiState.name,
//                    label = {
//                        Text("name")
//                    }, onValueChange = { it: String ->
//                        onEvent(ExerciseEvent.SetName(it))
//                    })
//                TextField(
//                    value = exerciseUiState.description,
//                    label = {
//                        Text("description")
//                    }, onValueChange = { it: String ->
//                        onEvent(ExerciseEvent.SetDescription(it))
//                    })
//            }
//        }
//    }
//}