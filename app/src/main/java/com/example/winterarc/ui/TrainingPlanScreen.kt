package com.example.winterarc.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.winterarc.data.model.TrainingPlan
import com.example.winterarc.ui.utils.ExerciseItemScreen
import com.example.winterarc.ui.utils.EmptyItemScreen
import com.example.winterarc.ui.utils.TrainingPlanItemScreen
import com.example.winterarc.ui.utils.WinterArcContentType

@Composable
fun TrainingPlanScreen(
    modifier: Modifier = Modifier,
    trainingPlanUiState: TrainingPlanUiState,
    exerciseUiState: ExerciseUiState,
    contentType: WinterArcContentType,
    onTrainingPlanItemListPressed: (TrainingPlan) -> Unit,
    onTrainingPlanItemScreenBackPressed: () -> Unit
) {
//    val navController = rememberNavController()
//    val navHost = movableContentOf<PaddingValues> { innerPadding ->
//        NavHost(navController = navController, startDestination = EmptyItemScreen) {
//            composable<EmptyItemScreen> {
//                WinterArcEmptyItemScreen()
//            }
//            composable<TrainingPlanItemScreen> {
//                val args = it.toRoute<TrainingPlanItemScreen>()
//                val selectedTrainingPlan: TrainingPlan? = trainingPlanUiState.trainingPlanMap.get(args.id)
//                WinterArcTrainingPlanItemScreen(
//                    modifier = Modifier.padding(innerPadding),
//                    trainingPlan = selectedTrainingPlan,
//                    onExercisePressed = { id ->
//                        navController.navigate(route = ExerciseItemScreen(id))
//                    },
//                    onBackPressed = {}
//                )
//            }
//            composable<ExerciseItemScreen> {
//                val args = it.toRoute<ExerciseItemScreen>()
//                        WinterArcExerciseItemScreen(
//                            modifier = Modifier.padding(
//                                innerPadding
//                            ),
//                            exercise = exerciseUiState.exercisesMap[args.id]
//                        )
//            }
//        }
////    }
//    if (trainingPlanUiState.isShowingList){
//        WinterArcListDetailTrainingPlansRoute(
//            contentType = contentType,
//            onTrainingPlanItemScreenBackPressed = onTrainingPlanItemScreenBackPressed,
//            isShowingList = trainingPlanUiState.isShowingList,
//            listOfItems = trainingPlanUiState.trainingPlanMap,
//            details = { navHost(PaddingValues()) },
//            listItem = { item ->
//                WinterArcListItem(
//                    title = item.name,
//                    subtitle = null,
//                    additionalInfo = null,
//                    onCardClick = {
//                        onTrainingPlanItemListPressed(item)
//                    }
//                )
//            }
//        )
//    }
    if (trainingPlanUiState.isShowingList){
        WinterArcListDetailTrainingPlansRoute(
            contentType = contentType,
            onTrainingPlanItemScreenBackPressed = onTrainingPlanItemScreenBackPressed,
            isShowingList = trainingPlanUiState.isShowingList,
            listOfItems = trainingPlanUiState.trainingPlanMap,
            details = { WinterArcTrainingPlanItemScreen(
                    modifier = Modifier.padding(PaddingValues()),
                    trainingPlan = trainingPlanUiState.currentTrainingPlan,
                    onExercisePressed = {
                    },
                    onBackPressed = {}
                ) },
            listItem = { item ->
                WinterArcListItem(
                    title = item.name,
                    subtitle = null,
                    additionalInfo = null,
                    onCardClick = {
                        onTrainingPlanItemListPressed(item)
                    }
                )
            }
        )
    }
//    Box(modifier = modifier) {
//        if (contentType == WinterArcContentType.LIST_AND_DETAIL) {
//            TrainingPlansListAndDetailContent (
//                trainingPlanUiState = trainingPlanUiState,
//                onTrainingPlanCardPressed = onTrainingPlanCardPressed,
//                onTrainingPlanItemScreenBackPressed = onTrainingPlanItemScreenBackPressed
//            )
//        } else {
//            TrainingPlansListOnlyContent (
//                trainingPlanUiState = trainingPlanUiState,
//                onTrainingPlanCardPressed = onTrainingPlanCardPressed,
//                onTrainingPlanItemScreenBackPressed = onTrainingPlanItemScreenBackPressed
//            )
//        }
//    }
}
