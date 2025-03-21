package com.example.winterarc.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FormatListNumbered
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun WinterArcHomeScreen(
    modifier: Modifier = Modifier
) {
    val bottomNavItems = listOf(
        // Home screen
        NavItem(
            label = "Training Plan",
            icon = Icons.Filled.FormatListNumbered,
            route = "trainingPlan"
        ),
        // Search screen
        NavItem(
            label = "Exercise",
            icon = Icons.Filled.FitnessCenter,
            route = "exercise"
        ),
        // Profile screen
        NavItem(
            label = "Profile",
            icon = Icons.Filled.Person,
            route = "profile"
        )
    )
    val navController = rememberNavController()
    Scaffold(
//        topBar = TopAppBar()
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                navigationItemContentList = bottomNavItems
            )
        },
        content = {padding ->
            NavHostContainer(navController = navController, padding = padding)
        }
    )
}

@Composable
fun WinterArcHomeScreenTopBar() {

}

//@Composable
//private fun NavigationRail(
//    currentTab: MailboxType,
//    onTabPressed: ((MailboxType) -> Unit),
//    navigationItemContentList: List<NavigationItemContent>,
//    modifier: Modifier = Modifier
//) {
//    NavigationRail(modifier = modifier) {
//        for (navItem in navigationItemContentList) {
//            NavigationRailItem(
//                selected = currentTab == navItem.mailboxType,
//                onClick = { onTabPressed(navItem.mailboxType) },
//                icon = {
//                    Icon(
//                        imageVector = navItem.icon,
//                        contentDescription = navItem.text
//                    )
//                }
//            )
//        }
//    }
//}

@Composable
private fun BottomNavigationBar(
    navController: NavHostController,
//    currentTab: MailboxType,
//    onTabPressed: ((MailboxType) -> Unit),
    navigationItemContentList: List<NavItem>,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier.background(MaterialTheme.colorScheme.surfaceContainer)) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        navigationItemContentList.forEach { navItem ->
            NavigationBarItem(
                selected = currentRoute == navItem.route,
                onClick = { navController.navigate(navItem.route) },
                icon = {
                    Icon(
                        imageVector = navItem.icon,
                        contentDescription = navItem.label
                    )
                },
                label = {
                    Text(text = navItem.label)
                },
                alwaysShowLabel = true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onSurface, // Icon color when selected
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant, // Icon color when not selected
                    selectedTextColor = MaterialTheme.colorScheme.onSurface, // Label color when selected
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer // Highlight color for selected item
                )
            )
        }
    }
}
@Composable
fun NavHostContainer(
    navController: NavHostController,
    padding: PaddingValues
) {

    NavHost(
        navController = navController,

        // set the start destination as home
        startDestination = "trainingPlan",

        // Set the padding provided by scaffold
        modifier = Modifier.padding(paddingValues = padding),

        builder = {

            // route : Home
            composable("trainingPlan") {
                TrainingPlanScreen()
            }

            // route : search
            composable("exercise") {
                ExercisesScreen()
            }

            // route : profile
            composable("profile") {
                ProfileScreen()
            }
        })
}

data class NavItem(
    // Text below icon
    val label: String,
    // Icon
    val icon: ImageVector,
    // Route to the specific screen
    val route: String,
)