package com.nvshink.winterarc.ui.screens.exercise

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.Chair
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nvshink.winterarc.R
import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.ui.components.exercise.ExerciseEditDialog
import com.nvshink.winterarc.ui.components.generic.WinterArcItemDetail
import com.nvshink.winterarc.ui.components.generic.WinterArcListDetailRoute
import com.nvshink.winterarc.ui.components.generic.WinterArcListItem
import com.nvshink.winterarc.ui.event.ExerciseEvent
import com.nvshink.winterarc.ui.screens.WinterArcEmptyItemScreen
import com.nvshink.winterarc.ui.utils.ExerciseItemScreen
import com.nvshink.winterarc.ui.utils.EmptyItemScreen
import com.nvshink.winterarc.ui.utils.WinterArcContentType
import com.nvshink.winterarc.ui.utils.WinterArcNavigationType
import com.nvshink.winterarc.ui.states.ExerciseUiState
import com.nvshink.winterarc.ui.utils.SortTypes

@Composable
fun ExercisesScreen(
    modifier: Modifier = Modifier,
    exerciseUiState: ExerciseUiState,
    exerciseScreenModifier: Modifier,
    contentType: WinterArcContentType,
    innerPadding: PaddingValues,
    navigationType: WinterArcNavigationType,
    onEvent: (ExerciseEvent) -> Unit
) {
    onEvent(ExerciseEvent.SetIsBigScreen(navigationType != WinterArcNavigationType.BOTTOM_NAVIGATION))
    val navController = rememberNavController()
    val navHost = movableContentOf<PaddingValues> {
        NavHost(navController = navController, startDestination = EmptyItemScreen) {
            composable<EmptyItemScreen> {
                WinterArcEmptyItemScreen(
                    title = stringResource(R.string.empty_screen_title_exercise),
                    icon = Icons.Filled.Chair,
                    iconDescription = stringResource(R.string.empty_screen_icon_description_exercise)
                )
            }
            composable<ExerciseItemScreen> {
                val args = it.toRoute<ExerciseItemScreen>()
                val exercise: Exercise? =
                    if (exerciseUiState is ExerciseUiState.SuccessState) exerciseUiState.exercisesMap[args.id] else null
                if (exercise != null) {
                    onEvent(ExerciseEvent.UpdateCurrentExercise(exercise))
                    onEvent(ExerciseEvent.HideList)
                    WinterArcExerciseItemScreen(
                        modifier = exerciseScreenModifier,
                        exerciseUiState = exerciseUiState,
                        onEditButtonClick = {
                            onEvent(ExerciseEvent.SetName(exercise.name))
                            onEvent(ExerciseEvent.SetDescription(exercise.description))
                            onEvent(ExerciseEvent.SetImages(exercise.images))
                            onEvent(ExerciseEvent.ShowDialog(false))
                        },
                        onDeleteButtonClick = {
                            onEvent(ExerciseEvent.ShowList)
                            navController.navigate(EmptyItemScreen)
                            onEvent(ExerciseEvent.UpdateCurrentExercise(null))
                            onEvent(ExerciseEvent.DeleteExercise(exercise))
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
    if (exerciseUiState.isShowingEditDialog) ExerciseEditDialog(
        exerciseUiState = exerciseUiState,
        title = stringResource(R.string.dialog_title_add_exercise),
        onEvent = onEvent
    )
    Box(modifier = modifier) {
        WinterArcListDetailRoute(
            modifier = Modifier.padding(innerPadding),
            contentType = contentType,
            isShowingList = exerciseUiState.isShowingList,
            listOfItems = if (exerciseUiState is ExerciseUiState.SuccessState) exerciseUiState.exercisesMap else mutableMapOf(),
            emptyListIcon = Icons.Filled.AddBox,
            emptyListIconDescription = stringResource(R.string.empty_list_icon_description_exercise),
            emptyListTitle = stringResource(R.string.empty_list_title_exercise),
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
            isLoading = exerciseUiState::class == ExerciseUiState.LoadingState::class,
            listTopContent = {
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(onClick = {
                        onEvent(
                            ExerciseEvent.SortExercises(
                                sortType = when (exerciseUiState.sortType) {
                                    SortTypes.NAME_ASC -> SortTypes.NAME_DESC
                                    SortTypes.NAME_DESC -> SortTypes.NAME_ASC
                                }
                            )
                        )
                    }) { Text(text = "Sort " + stringResource(exerciseUiState.sortType.stringResourceName)) }
                    Text(text = "Now sorted " + exerciseUiState.sortType.name)
                }
            },
            fab = {
                FloatingActionButton({
                    onEvent(ExerciseEvent.SetName(""))
                    onEvent(ExerciseEvent.SetDescription(""))
                    onEvent(ExerciseEvent.SetImages(emptyList()))
                    onEvent(ExerciseEvent.ShowDialog(true))
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

