package com.nvshink.winterarc.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.ui.components.WinterArcItemDetail
import com.nvshink.winterarc.ui.components.WinterArcListDetailRoute
import com.nvshink.winterarc.ui.components.WinterArcListItem
import com.nvshink.winterarc.ui.utils.ExerciseItemScreen
import com.nvshink.winterarc.ui.utils.EmptyItemScreen
import com.nvshink.winterarc.ui.utils.WinterArcContentType
import com.nvshink.winterarc.ui.viewModel.ExerciseUiState

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

