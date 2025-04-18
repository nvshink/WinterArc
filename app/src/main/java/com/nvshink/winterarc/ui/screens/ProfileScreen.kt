package com.nvshink.winterarc.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nvshink.winterarc.ui.utils.WinterArcContentType

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    contentType: WinterArcContentType,
    onTrainingPlanItemScreenBackPressed: () -> Unit
) {
    Text("Profile")
}
