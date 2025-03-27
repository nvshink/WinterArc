package com.example.winterarc.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.example.winterarc.ui.utils.DEFAULT_DESTINATION
import com.example.winterarc.ui.utils.WinterArcContentType
import com.example.winterarc.ui.utils.WinterArcDestinations

@Composable
fun WinterArcApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    val contentType: WinterArcContentType
    val navController = rememberNavController()
    val navHost = remember {
        movableContentOf<PaddingValues> { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = DEFAULT_DESTINATION.route
            ) {
                composable(route = WinterArcDestinations.TRAINING_PLAN.route) {
                    TrainingPlanScreen()
                }
                composable(route = WinterArcDestinations.EXERCISES.route) {
                    ExercisesScreen(modifier = Modifier.padding(innerPadding))
                }
                composable(route = WinterArcDestinations.PROFILE.route) {
                    ProfileScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    val winterArcNavigationBarLayout = @Composable {
        WinterArcNavigationBarLayout(
            modifier = Modifier,
            destination =  navController.currentDestination ?: NavDestination(DEFAULT_DESTINATION.route),
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
    val winterArcNavigationRailLayout = @Composable {
        WinterArcNavigationRailLayout(
            modifier = Modifier,
            destination = navController.currentDestination ?: NavDestination(DEFAULT_DESTINATION.route),
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

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            contentType = WinterArcContentType.LIST_ONLY
            winterArcNavigationBarLayout()
        }

        WindowWidthSizeClass.Medium -> {
            contentType = WinterArcContentType.LIST_ONLY
            winterArcNavigationRailLayout()
        }

        WindowWidthSizeClass.Expanded -> {
            contentType = WinterArcContentType.LIST_AND_DETAIL
            winterArcNavigationRailLayout()
        }

        else -> {
            contentType = WinterArcContentType.LIST_ONLY
            winterArcNavigationBarLayout()
        }
    }
}

@Composable
fun WinterArcNavigationBarLayout(
    modifier: Modifier,
    destination: NavDestination,
    onMenuItemSelected: (String) -> Unit,
    content: @Composable (innerPadding: PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomAppBar {
                WinterArcDestinations.entries.forEach { item ->
                    NavigationBarItem(
                        label = {
                            Text(text = item.label.toString())
                        },
                        icon = {
                            Icon(imageVector = item.icon, contentDescription = item.label.toString())
                        },
                        selected = destination.route == item.route,
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
    destination: NavDestination,
    onMenuItemSelected: (String) -> Unit,
    content: @Composable () -> Unit
) {
    Row(modifier = modifier.fillMaxSize()) {
        NavigationRail {
            WinterArcDestinations.entries.forEach { item ->
                NavigationRailItem(
                    label = {
                        Text(text = item.label.toString())
                    },
                    icon = {
                        Icon(imageVector = item.icon, contentDescription = item.label.toString())
                    },
                    selected = destination.route == item.route,
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
fun WinterArcListDetailRoute(
    modifier: Modifier = Modifier,
    contentType: WinterArcContentType = WinterArcContentType.LIST_ONLY,
    onTrainingPlanItemScreenBackPressed: () -> Unit,
    selectedItemId: String?
) {
    if (contentType == WinterArcContentType.LIST_ONLY) {
        if (selectedItemId != null) {
            WinterArcItemDetail(selectedItemId)
            BackHandler { onTrainingPlanItemScreenBackPressed() }
        } else {
            WinterArcListOfItems()
        }
    } else {
        WinterArcListAndDetail(selectedItemId)
    }
}

@Composable
fun WinterArcListAndDetail(
    selectedItemId: String?
) {

}

@Composable
fun WinterArcItemDetail(
    selectedItemId: String?
) {
    val navController = rememberNavController()
    NavHost(navController, "landing_screen_route") {
        composable("landing_screen_route") {
            WinterArcTestScreen("1") { navController.navigate("second_screen_route") }
        }
        composable("second_screen_route", deepLinks = listOf(
            navDeepLink {
                uriPattern = "" //TODO
            }
        )) {
            WinterArcTestScreen("2") { navController.navigate("second_screen_route") }
        }
    }
}

@Composable
fun WinterArcTestScreen(
    screenName: String,
    onButtonClick: () -> Unit
) {
    Text(screenName)
    Button({
        onButtonClick()
    }) { Text("next screen") }
}

@Composable
fun WinterArcListOfItems() {

}

@Composable
fun WinterArcListItem(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    image: ImageBitmap? = null,
    onCardClick: (() -> Unit)? = null,
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

