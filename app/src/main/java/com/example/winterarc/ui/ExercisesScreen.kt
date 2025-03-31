package com.example.winterarc.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.winterarc.data.model.Exercise
import com.example.winterarc.ui.utils.ExerciseItemScreen
import com.example.winterarc.ui.utils.EmptyItemScreen
import com.example.winterarc.ui.utils.WinterArcContentType

@Composable
fun ExercisesScreen(
    modifier: Modifier = Modifier,
    exerciseUiState: ExerciseUiState,
    contentType: WinterArcContentType,
    onExerciseItemListPressed: (Exercise) -> Unit,
    onExerciseItemScreenBackPressed: () -> Unit
) {
    val navController = rememberNavController()
    val navHost = movableContentOf<PaddingValues> { innerPadding ->
        NavHost(navController = navController, startDestination = EmptyItemScreen) {
            composable<EmptyItemScreen> {
                WinterArcEmptyItemScreen()
            }
            composable<ExerciseItemScreen> {
                val args = it.toRoute<ExerciseItemScreen>()
                WinterArcExerciseItemScreen(
                    modifier = Modifier.padding(
                        innerPadding
                    ),
                    exercise = exerciseUiState.exercisesMap[args.id]
                )
            }
        }
    }
    WinterArcListDetailTrainingPlansRoute(
        contentType = contentType,
        isShowingList = exerciseUiState.isShowingList,
        listOfItems = exerciseUiState.exercisesMap,
        details = {
            WinterArcExerciseItemScreen(
                exercise =  exerciseUiState.currentExercise
            )
        },
        onTrainingPlanItemScreenBackPressed = onExerciseItemScreenBackPressed,
        listItem = { item ->
            WinterArcListItem(
                title = item.name,
                subtitle = null,
                additionalInfo = null,
                onCardClick = {
//                    navController.navigate(route = ExerciseItemScreen(item.id))
                    onExerciseItemListPressed(item)
                }
            )
        }
    )
}

