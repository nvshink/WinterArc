package com.example.winterarc.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun WinterArcApp (
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    WinterArcHomeScreen()
}