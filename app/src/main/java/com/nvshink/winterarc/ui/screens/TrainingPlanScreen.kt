package com.nvshink.winterarc.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.movableContentOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nvshink.winterarc.data.model.TrainingPlan
import com.nvshink.winterarc.ui.components.WinterArcItemDetail
import com.nvshink.winterarc.ui.components.WinterArcListDetailRoute
import com.nvshink.winterarc.ui.components.WinterArcListItem
import com.nvshink.winterarc.ui.utils.ExerciseItemScreen
import com.nvshink.winterarc.ui.utils.EmptyItemScreen
import com.nvshink.winterarc.ui.utils.TrainingPlanItemScreen
import com.nvshink.winterarc.ui.utils.WinterArcContentType
import com.nvshink.winterarc.ui.viewModel.ExerciseUiState
import com.nvshink.winterarc.ui.viewModel.ExerciseViewModel
import com.nvshink.winterarc.ui.viewModel.TrainingPlanUiState

@Composable
fun TrainingPlanScreen(
    modifier: Modifier = Modifier,
    trainingPlanUiState: TrainingPlanUiState,
    exerciseUiState: ExerciseUiState,
    contentType: WinterArcContentType,
    onTrainingPlanItemListPressed: (TrainingPlan) -> Unit,
    onTrainingPlanItemScreenBackPressed: () -> Unit
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
                    trainingPlanUiState.trainingPlanMap.get(args.id)
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
                WinterArcExerciseItemScreen(
                    exercise = exerciseUiState.exercisesMap[args.id],
                    exerciseUiState = exerciseUiState,
                    onBackPressed = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
    WinterArcListDetailRoute(
        modifier = modifier.padding(horizontal = 10.dp),
        contentType = contentType,
        onItemScreenBackPressed = onTrainingPlanItemScreenBackPressed,
        isShowingList = trainingPlanUiState.isShowingList,
        listOfItems = trainingPlanUiState.trainingPlanMap,
        listArrangement = 10.dp,
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
