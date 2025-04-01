package com.example.winterarc.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.movableContentOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.winterarc.data.model.TrainingPlan
import com.example.winterarc.ui.viewModel.ExerciseViewModel
import com.example.winterarc.ui.components.WinterArcItemDetail
import com.example.winterarc.ui.components.WinterArcListDetailRoute
import com.example.winterarc.ui.components.WinterArcListItem
import com.example.winterarc.ui.utils.ExerciseItemScreen
import com.example.winterarc.ui.utils.EmptyItemScreen
import com.example.winterarc.ui.utils.TrainingPlanItemScreen
import com.example.winterarc.ui.utils.WinterArcContentType
import com.example.winterarc.ui.viewModel.TrainingPlanUiState

@Composable
fun TrainingPlanScreen(
    modifier: Modifier = Modifier,
    trainingPlanUiState: TrainingPlanUiState,
    contentType: WinterArcContentType,
    onTrainingPlanItemListPressed: (TrainingPlan) -> Unit,
    onTrainingPlanItemScreenBackPressed: () -> Unit
) {
    val exerciseViewModel: ExerciseViewModel = viewModel()
    val exerciseUiState = exerciseViewModel.uiState.collectAsState().value
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
