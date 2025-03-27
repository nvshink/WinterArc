package com.example.winterarc.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.winterarc.data.model.Exercise
import com.example.winterarc.data.model.TrainingPlan
import com.example.winterarc.ui.utils.WinterArcContentType
import com.example.winterarc.ui.utils.WinterArcDestinations

@Composable
fun WinterArcHomeScreen(
    modifier: Modifier = Modifier,
    navigationType: NavigationSuiteType,
    contentType: WinterArcContentType
) {
    var currentDestination by rememberSaveable { mutableStateOf(WinterArcDestinations.TRAINING_PLAN) }
    val exerciseViewModel: ExerciseViewModel = viewModel()
    val exerciseUiState by exerciseViewModel.uiState.collectAsState()
    val trainingPlanViewModel: TrainingPlanViewModel = viewModel()
    val trainingPlanUiState by trainingPlanViewModel.uiState.collectAsState()

//    val navController = rememberNavController()
//    NavHost(
//        navController = navController,
//        startDestination = "training-plans") {
//        composable("training-plans") {
//            TrainingPlanScreen(
//                modifier = Modifier.padding(),
//                trainingPlanUiState = trainingPlanUiState,
//                contentType = contentType,
//                onTrainingPlanCardPressed = { trainingPlan: TrainingPlan ->
//                    trainingPlanViewModel.updateTrainingPlanItemState(trainingPlan)
//                },
//                onTrainingPlanItemScreenBackPressed = {
//                    trainingPlanViewModel.resetTrainingPlansListState()
//                }
//            )
//        }


//    }


}

//@Composable
//fun WinterArcHomeScreenTopBar() {
//
//}
//
//@Composable
//private fun WinterArcNavigationRail(
//    navController: NavHostController,
//    navigationItemContentList: List<NavItem>,
//    modifier: Modifier = Modifier
//) {
//    NavigationRail(modifier = modifier.background(MaterialTheme.colorScheme.surfaceContainer)) {
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentRoute = navBackStackEntry?.destination?.route
//        navigationItemContentList.forEach { navItem ->
//            NavigationRailItem(
//                selected = currentRoute == navItem.route,
//                onClick = { navController.navigate(navItem.route) },
//                icon = {
//                    Icon(
//                        imageVector = navItem.icon,
//                        contentDescription = navItem.label
//                    )
//                },
//                label = {
//                    Text(text = navItem.label)
//                },
//                alwaysShowLabel = true,
//                colors = NavigationRailItemDefaults.colors(
//                    selectedIconColor = MaterialTheme.colorScheme.onSurface, // Icon color when selected
//                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant, // Icon color when not selected
//                    selectedTextColor = MaterialTheme.colorScheme.onSurface, // Label color when selected
//                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
//                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer // Highlight color for selected item
//                )
//            )
//        }
//    }
//}
//
//@Composable
//private fun WinterArcNavigationBar(
//    navController: NavHostController,
//    navigationItemContentList: List<NavItem>,
//    modifier: Modifier = Modifier
//) {
//    NavigationBar(modifier = modifier.background(MaterialTheme.colorScheme.surfaceContainer)) {
//        val navBackStackEntry by navController.currentBackStackEntryAsState()
//        val currentRoute = navBackStackEntry?.destination?.route
//        navigationItemContentList.forEach { navItem ->
//            NavigationBarItem(
//                selected = currentRoute == navItem.route,
//                onClick = { navController.navigate(navItem.route) },
//                icon = {
//                    Icon(
//                        imageVector = navItem.icon,
//                        contentDescription = navItem.label
//                    )
//                },
//                label = {
//                    Text(text = navItem.label)
//                },
//                alwaysShowLabel = true,
//                colors = NavigationBarItemDefaults.colors(
//                    selectedIconColor = MaterialTheme.colorScheme.onSurface, // Icon color when selected
//                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant, // Icon color when not selected
//                    selectedTextColor = MaterialTheme.colorScheme.onSurface, // Label color when selected
//                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
//                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer // Highlight color for selected item
//                )
//            )
//        }
//    }
//}
//
//@Composable
//fun NavHostContainer(
//    navController: NavHostController,
//    padding: PaddingValues
//) {
//    NavHost(
//        navController = navController,
//
//        // set the start destination as home
//        startDestination = "trainingPlan",
//
//        // Set the padding provided by scaffold
//        modifier = Modifier.padding(paddingValues = padding),
//
//        builder = {
//
//            // route : Home
//            composable("trainingPlan") {
//                TrainingPlanScreen()
//            }
//
//            // route : search
//            composable("exercise") {
//                ExercisesScreen()
//            }
//
//            // route : profile
//            composable("profile") {
//                ProfileScreen()
//            }
//        })
//}
//
//data class NavItem(
//    // Text below icon
//    val label: String,
//    // Icon
//    val icon: ImageVector,
//    // Route to the specific screen
//    val route: String,
//)


//        NavigationSuiteScaffold(
//            modifier = modifier,
//            layoutType = navigationType,
//            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
//            navigationSuiteItems = {
//                WinterArcDestinations.entries.forEach {
//                    item(
//                        modifier = Modifier.widthIn(min = 50.dp, max = 200.dp).padding(10.dp),
//                        icon = {
//                            Icon(
//                                it.icon,
//                                contentDescription = stringResource(it.contentDescription)
//                            )
//                        },
//                        label = { Text(stringResource(it.label)) },
//                        selected = it == currentDestination,
//                        onClick = { currentDestination = it }
//                    )
//                }
//            }
//        ) {
//            when (currentDestination) {
//                WinterArcDestinations.TRAINING_PLAN -> TrainingPlanScreen(
//                    modifier = Modifier.padding(paddingValues),
//                    trainingPlanUiState = trainingPlanUiState,
//                    contentType = contentType,
//                    onTrainingPlanCardPressed = { trainingPlan: TrainingPlan ->
//                        trainingPlanViewModel.updateTrainingPlanItemState(trainingPlan)
//                    },
//                    onTrainingPlanItemScreenBackPressed = {
//                        trainingPlanViewModel.resetTrainingPlansListState()
//                    }
//                )
//                WinterArcDestinations.EXERCISES -> ExercisesScreen(
//                    modifier = Modifier.padding(paddingValues),
//                    exerciseUiState = exerciseUiState,
//                    contentType = contentType,
//                    onExerciseCardPressed = { exercise: Exercise ->
//                        exerciseViewModel.updateExerciseItemState(exercise = exercise)
//                    },
//                    onExerciseItemScreenBackPressed = {
//                        exerciseViewModel.resetExercisesListItemState()
//                    }
//                )
//
//                WinterArcDestinations.PROFILE -> ProfileScreen(Modifier.padding(paddingValues))
//            }
//        }