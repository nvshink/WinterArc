package com.nvshink.winterarc.ui.screens.trainingplan

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Chair
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.movableContentOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nvshink.winterarc.R
import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.data.model.TrainingPlan
import com.nvshink.winterarc.ui.components.generic.WinterArcItemDetail
import com.nvshink.winterarc.ui.components.generic.WinterArcListDetailRoute
import com.nvshink.winterarc.ui.components.generic.WinterArcListItem
import com.nvshink.winterarc.ui.components.trainingplan.TrainingPlanEditDialog
import com.nvshink.winterarc.ui.event.ExerciseEvent
import com.nvshink.winterarc.ui.event.TrainingPlanEvent
import com.nvshink.winterarc.ui.screens.WinterArcEmptyItemScreen
import com.nvshink.winterarc.ui.screens.exercise.WinterArcExerciseItemScreen
import com.nvshink.winterarc.ui.states.ExerciseUiState
import com.nvshink.winterarc.ui.utils.ExerciseItemScreen
import com.nvshink.winterarc.ui.utils.EmptyItemScreen
import com.nvshink.winterarc.ui.utils.TrainingPlanItemScreen
import com.nvshink.winterarc.ui.utils.WinterArcContentType
import com.nvshink.winterarc.ui.viewModels.ExerciseViewModel
import com.nvshink.winterarc.ui.states.TrainingPlanUiState

@Composable
fun TrainingPlanScreen(
    modifier: Modifier = Modifier,
    trainingPlanUiState: TrainingPlanUiState,
    trainingPlanScreenModifier: Modifier,
    contentType: WinterArcContentType,
    innerPadding: PaddingValues,
//    onTrainingPlanItemListPressed: (TrainingPlan) -> Unit,
//    onTrainingPlanItemScreenBackPressed: () -> Unit,
    onEvent: (TrainingPlanEvent) -> Unit,
    onExerciseEvent: (ExerciseEvent) -> Unit,
) {
    val navController = rememberNavController()
    val navHost = movableContentOf<PaddingValues> {
        NavHost(navController = navController, startDestination = EmptyItemScreen) {
            composable<EmptyItemScreen> {
                WinterArcEmptyItemScreen(
                    title = stringResource(R.string.empty_screen_title_training_plan),
                    icon = Icons.Filled.Chair,
                    iconDescription = stringResource(R.string.empty_screen_icon_description_training_plan)
                )
            }
            composable<TrainingPlanItemScreen> {
                val args = it.toRoute<TrainingPlanItemScreen>()
                WinterArcTrainingPlanItemScreen(
                    modifier = trainingPlanScreenModifier,
                    trainingPlanUiState = trainingPlanUiState,
                    onExercisePressed = { id ->
                        navController.navigate(route = ExerciseItemScreen(id))
                    },
                    onBackPressed = {}
                )
            }
            composable<ExerciseItemScreen> {
                val args = it.toRoute<ExerciseItemScreen>()
                val exerciseViewModel: ExerciseViewModel = hiltViewModel()
                val exerciseItemScreenUiState = exerciseViewModel.uiState.collectAsState().value
                if (exerciseItemScreenUiState is ExerciseUiState.SuccessState) {
                    val exercise: Exercise? = exerciseItemScreenUiState.exercisesMap[args.id]
                    if (exercise != null) {
                        onExerciseEvent(ExerciseEvent.UpdateCurrentExercise(exercise))
                        WinterArcExerciseItemScreen(
                            exerciseUiState = exerciseItemScreenUiState,
                            onEditButtonClick = {
                                onExerciseEvent(ExerciseEvent.ShowDialog(isAdding = false))
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
    }
    if (trainingPlanUiState.isShowingEditDialog && trainingPlanUiState is TrainingPlanUiState.SuccessState) TrainingPlanEditDialog(
        trainingPlanUiState = trainingPlanUiState,
        title = stringResource(R.string.dialog_title_add_training_plan),
        onEvent = onEvent
    )
    Surface(modifier = modifier) {
        WinterArcListDetailRoute(
            modifier = Modifier.padding(innerPadding),
            contentType = contentType,
            isShowingList = trainingPlanUiState.isShowingList,
            listOfItems = if (trainingPlanUiState is TrainingPlanUiState.SuccessState) trainingPlanUiState.trainingPlansMap else emptyMap(),
            emptyListIcon = Icons.Filled.AddBox,
            emptyListIconDescription = stringResource(R.string.empty_list_icon_description_training_plan),
            emptyListTitle = stringResource(R.string.empty_list_title_training_plan),
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
//                        onTrainingPlanItemListPressed(item)
                        navController.navigate(route = TrainingPlanItemScreen(item.id))
                    }
                )
            },
//            isLoading = trainingPlanUiState::class == TrainingPlanUiState.Loading::class,
            isLoading = false,
            fab = {
                FloatingActionButton({
                    onEvent(TrainingPlanEvent.ShowDialog(true))
                }, modifier = it) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = stringResource(R.string.add_exercise_button_icon_description)
                    )
                }
            }
        )
    }
}

