package com.example.winterarc.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    Box(modifier = modifier){
        Text("Profile screen")
        Button({navController.navigate("training-plans")}) { }
    }
}
