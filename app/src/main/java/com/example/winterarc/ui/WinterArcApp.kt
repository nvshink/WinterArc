package com.example.winterarc.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.winterarc.data.model.Exercise
import com.example.winterarc.data.model.TrainingPlan
import com.example.winterarc.ui.components.WinterArcNavigationBarLayout
import com.example.winterarc.ui.components.WinterArcNavigationRailLayout
import com.example.winterarc.ui.screens.ExercisesScreen
import com.example.winterarc.ui.screens.ProfileScreen
import com.example.winterarc.ui.screens.TrainingPlanScreen
import com.example.winterarc.ui.utils.ExerciseItemScreen
import com.example.winterarc.ui.viewModel.TrainingPlanViewModel
import com.example.winterarc.ui.utils.ExerciseScreenRoute
import com.example.winterarc.ui.utils.ProfileScreenRoute
import com.example.winterarc.ui.utils.TrainingPlanScreenRoute
import com.example.winterarc.ui.utils.WinterArcContentType
import com.example.winterarc.ui.utils.WinterArcDestinations
import com.example.winterarc.ui.utils.WinterArcNavigationType
import com.example.winterarc.ui.viewModel.ExerciseViewModel
import com.example.winterarc.ui.viewModel.ProfileViewModel

@Composable
fun WinterArcApp(
    windowSize: WindowWidthSizeClass
) {
    val contentType: WinterArcContentType
    val navigationType: WinterArcNavigationType

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            contentType = WinterArcContentType.LIST_ONLY
            navigationType = WinterArcNavigationType.BOTTOM_NAVIGATION
        }

        WindowWidthSizeClass.Medium -> {
            contentType = WinterArcContentType.LIST_ONLY
            navigationType = WinterArcNavigationType.NAVIGATION_RAIL
        }

        WindowWidthSizeClass.Expanded -> {
            contentType = WinterArcContentType.LIST_AND_DETAIL
            navigationType = WinterArcNavigationType.NAVIGATION_RAIL
        }

        else -> {
            contentType = WinterArcContentType.LIST_ONLY
            navigationType = WinterArcNavigationType.BOTTOM_NAVIGATION
        }
    }

    val navController = rememberNavController()
    val navHost = remember {
        movableContentOf<PaddingValues> { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = WinterArcDestinations.getDefaultTopLevelRoute().route
            ) {
                composable<TrainingPlanScreenRoute> {
                    val trainingPlanViewModel: TrainingPlanViewModel = viewModel()
                    val trainingPlanUiState = trainingPlanViewModel.uiState.collectAsState().value
                    TrainingPlanScreen(
                        modifier = Modifier.padding(innerPadding),
                        trainingPlanUiState = trainingPlanUiState,
                        contentType = contentType,
                        onTrainingPlanItemListPressed = { trainingPlan: TrainingPlan ->
                            trainingPlanViewModel.updateTrainingPlanItemState(trainingPlan)
                        },
                        onTrainingPlanItemScreenBackPressed = {
                            trainingPlanViewModel.resetTrainingPlansListState()
                        })
                }
                composable<ExerciseScreenRoute> {
                    val exerciseViewModel: ExerciseViewModel = viewModel()
                    val exerciseUiState = exerciseViewModel.uiState.collectAsState().value
                    ExercisesScreen(
                        modifier = Modifier.padding(innerPadding),
                        exerciseUiState = exerciseUiState,
                        contentType = contentType,
                        onExerciseItemListPressed = { exercise: Exercise ->
                            exerciseViewModel.updateExerciseItemState(exercise)
                        },
                        onExerciseItemScreenBackPressed = {
                            exerciseViewModel.resetExercisesListItemState()
                        })
                }
                composable<ProfileScreenRoute> {
                    val profileViewModel: ProfileViewModel = viewModel()
                    val profilePlanUiState = profileViewModel.uiState.collectAsState().value
                    ProfileScreen(
                        modifier = Modifier.padding(innerPadding),
                        contentType = contentType,
                        onTrainingPlanItemScreenBackPressed = {})
                }
            }
        }
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val winterArcNavigationBarLayout = @Composable {
        WinterArcNavigationBarLayout(
            modifier = Modifier,
            currentDestination = currentDestination,
            onMenuItemSelected = {
                navController.navigate(route = it) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        ) { innerPadding ->
            navHost(innerPadding)
        }
    }
    val winterArcNavigationRailLayout = @Composable {
        WinterArcNavigationRailLayout(
            modifier = Modifier,
            currentDestination = currentDestination,
            onMenuItemSelected = {
                navController.navigate(it) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        ) { innerPadding ->
            navHost(innerPadding)
        }
    }

    when (navigationType) {
        WinterArcNavigationType.BOTTOM_NAVIGATION -> {
            winterArcNavigationBarLayout()
        }

        WinterArcNavigationType.NAVIGATION_RAIL -> {
            winterArcNavigationRailLayout()
        }

        else -> {
            winterArcNavigationBarLayout()
        }
    }

}


