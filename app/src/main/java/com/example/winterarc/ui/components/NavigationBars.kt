package com.example.winterarc.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.winterarc.ui.utils.WinterArcDestinations

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
    content: @Composable (innerPadding: PaddingValues) -> Unit
) {
    val topLevelRoutes = WinterArcDestinations.getTopLevelRoutes()
    Scaffold { innerPadding ->
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
            content(innerPadding)
        }
    }
}