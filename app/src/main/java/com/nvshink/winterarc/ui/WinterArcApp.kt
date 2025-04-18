package com.nvshink.winterarc.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nvshink.winterarc.data.model.TrainingPlan
import com.nvshink.winterarc.data.room.WinterArcDatabase
import com.nvshink.winterarc.ui.components.WinterArcNavigationBarLayout
import com.nvshink.winterarc.ui.components.WinterArcNavigationRailLayout
import com.nvshink.winterarc.ui.screens.exercise.ExercisesScreen
import com.nvshink.winterarc.ui.screens.ProfileScreen
import com.nvshink.winterarc.ui.screens.TrainingPlanScreen
import com.nvshink.winterarc.ui.viewModel.TrainingPlanViewModel
import com.nvshink.winterarc.ui.utils.ExerciseScreenRoute
import com.nvshink.winterarc.ui.utils.ProfileScreenRoute
import com.nvshink.winterarc.ui.utils.TrainingPlanScreenRoute
import com.nvshink.winterarc.ui.utils.WinterArcContentType
import com.nvshink.winterarc.ui.utils.WinterArcDestinations
import com.nvshink.winterarc.ui.utils.WinterArcNavigationType
import com.nvshink.winterarc.ui.viewModel.ExerciseViewModel
import com.nvshink.winterarc.ui.viewModel.ProfileViewModel

@Composable
fun WinterArcApp(
    windowSize: WindowWidthSizeClass,
    db: WinterArcDatabase,
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
                    val trainingPlanViewModel: TrainingPlanViewModel = hiltViewModel()
                    val trainingPlanUiState = trainingPlanViewModel.uiState.collectAsState().value
                    val exerciseViewModel: ExerciseViewModel = hiltViewModel()
                    val exerciseUiState = exerciseViewModel. uiState.collectAsState().value
                    TrainingPlanScreen(
                        modifier = Modifier.padding(innerPadding),
                        trainingPlanUiState = trainingPlanUiState,
                        exerciseUiState = exerciseUiState,
                        contentType = contentType,
                        onTrainingPlanItemListPressed = { trainingPlan: TrainingPlan ->
                            trainingPlanViewModel.updateTrainingPlanItemState(trainingPlan)
                        },
                        onTrainingPlanItemScreenBackPressed = {
                            trainingPlanViewModel.resetTrainingPlansListState()
                        },
                        onExerciseEvent = {
                            exerciseViewModel.resetExercisesListItemState()
                        }
                    )
                }
                composable<ExerciseScreenRoute> {
                    val exerciseViewModel: ExerciseViewModel = hiltViewModel()
                    val exerciseUiState = exerciseViewModel. uiState.collectAsState().value
                    ExercisesScreen(
                        modifier = Modifier.padding(innerPadding),
                        exerciseUiState = exerciseUiState,
                        contentType = contentType,
                        onEvent = exerciseViewModel::onEvent
                    )
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


