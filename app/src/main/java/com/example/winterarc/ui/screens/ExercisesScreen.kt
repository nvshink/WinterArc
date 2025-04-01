package com.example.winterarc.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.winterarc.data.model.Exercise
import com.example.winterarc.ui.components.WinterArcItemDetail
import com.example.winterarc.ui.components.WinterArcListDetailRoute
import com.example.winterarc.ui.components.WinterArcListItem
import com.example.winterarc.ui.utils.ExerciseItemScreen
import com.example.winterarc.ui.utils.EmptyItemScreen
import com.example.winterarc.ui.utils.WinterArcContentType
import com.example.winterarc.ui.viewModel.ExerciseUiState

@Composable
fun ExercisesScreen(
    modifier: Modifier = Modifier,
    exerciseUiState: ExerciseUiState,
    contentType: WinterArcContentType,
    onExerciseItemListPressed: (Exercise) -> Unit,
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
                    onBackPressed = onExerciseItemScreenBackPressed
                )
            }
        }
    }
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
                    onExerciseItemListPressed(item)
                }
            )
        }
    )
}

