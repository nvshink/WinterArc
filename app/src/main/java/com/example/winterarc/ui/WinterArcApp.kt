package com.example.winterarc.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.winterarc.data.model.Exercise
import com.example.winterarc.data.model.TrainingPlan
import com.example.winterarc.ui.utils.ExerciseScreenRoute
import com.example.winterarc.ui.utils.ProfileScreenRoute
import com.example.winterarc.ui.utils.TrainingPlanScreenRoute
import com.example.winterarc.ui.utils.WinterArcContentType
import com.example.winterarc.ui.utils.WinterArcDestinations
import com.example.winterarc.ui.utils.WinterArcNavigationType

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
                startDestination = TrainingPlanScreenRoute
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
        ) {
            navHost(PaddingValues())
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

@Composable
fun WinterArcNavigationBarLayout(
    modifier: Modifier,
    currentDestination: NavDestination?,
    onMenuItemSelected: (Any) -> Unit,
    content: @Composable (innerPadding: PaddingValues) -> Unit
) {
    val topLevelRoutes = WinterArcDestinations.getTopLevelRoutes()
    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomAppBar {
                topLevelRoutes.forEach { item ->
                    NavigationBarItem(
                        label = {
                            Text(text = item.name)
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name
                            )
                        },
                        selected = currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true,
                        onClick = {
                            onMenuItemSelected(item.route)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}

@Composable
fun WinterArcNavigationRailLayout(
    modifier: Modifier,
    currentDestination: NavDestination?,
    onMenuItemSelected: (Any) -> Unit,
    content: @Composable () -> Unit
) {
    val topLevelRoutes = WinterArcDestinations.getTopLevelRoutes()
    Row(modifier = modifier.fillMaxSize()) {
        NavigationRail {
            topLevelRoutes.forEach { item ->
                NavigationRailItem(
                    label = {
                        Text(text = item.name)
                    },
                    icon = {
                        Icon(imageVector = item.icon, contentDescription = item.name)
                    },
                    selected = currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true,
                    onClick = {
                        onMenuItemSelected(item.route)
                    }
                )
            }
        }
        content()
    }
}

@Composable
fun <T> WinterArcListDetailRoute(
    modifier: Modifier = Modifier,
    isShowingList: Boolean,
    details: @Composable () -> Unit,
    listOfItems: MutableMap<Int, T>,
    listItem: @Composable (T) -> Unit,
    contentType: WinterArcContentType = WinterArcContentType.LIST_ONLY,
    onItemScreenBackPressed: () -> Unit,
) {
    Box(modifier = modifier) {
        if (contentType == WinterArcContentType.LIST_ONLY) {
            if (isShowingList) {
                WinterArcListOfItems(
                    listOfItems = listOfItems,
                    listItem = listItem
                )
            } else {
                BackHandler { onItemScreenBackPressed() }
            }
            WinterArcItemDetail (
                modifier = Modifier.alpha(if (isShowingList) 0f else 1f),
                details = details
            )
        } else {
            WinterArcListAndDetail(
                listOfItems = listOfItems,
                listItem = listItem,
                details = details
            )
            BackHandler { onItemScreenBackPressed() }
        }
    }
}

@Composable
fun <T> WinterArcListAndDetail(
    modifier: Modifier = Modifier,
    listOfItems: MutableMap<Int, T>,
    listItem: @Composable (T) -> Unit,
    details: @Composable () -> Unit,
) {
    Row(modifier = modifier) {
        WinterArcListOfItems(
            modifier = Modifier.weight(1f),
            listOfItems = listOfItems,
            listItem = listItem
        )
        WinterArcItemDetail (
            modifier = Modifier.weight(1f),
            details = details
        )
    }
}

@Composable
fun WinterArcItemDetail(
    modifier: Modifier = Modifier,
    details: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        details()
    }
}


@Composable
fun WinterArcEmptyItemScreen(
) {
    Text("Empty screen")
}

@Composable
fun <T> WinterArcListOfItems(
    modifier: Modifier = Modifier,
    listOfItems: MutableMap<Int, T>,
    listItem: @Composable (T) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(listOfItems.toList()) { item ->
            listItem(item.second)
        }
    }
}

@Composable
fun WinterArcListItem(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String?,
    image: ImageBitmap? = null,
    onCardClick: (() -> Unit)?,
    additionalInfo: @Composable (() -> Unit)? = null
) {
    if (onCardClick != null) {
        OutlinedCard(
            modifier = modifier
                .fillMaxWidth()
                .height(80.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            onClick = onCardClick
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Column(Modifier) {
                    Text(text = title, style = MaterialTheme.typography.titleMedium)
                    Text(text = subtitle ?: "", style = MaterialTheme.typography.bodyMedium)
                }
                if (additionalInfo != null) additionalInfo()
                if (image != null) {
                    Image(image, contentDescription = null)
                }
            }
        }
    }
}

@Composable
fun WinterArcItemScreenTopBar(
    modifier: Modifier = Modifier,
    onBackButtonClicked: (() -> Unit)? = null,
    actions: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onBackButtonClicked != null) {
            IconButton(onClick = { onBackButtonClicked() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "backButton")
            }
        }
        Row {
            if (actions != null) actions()
        }
    }
}
