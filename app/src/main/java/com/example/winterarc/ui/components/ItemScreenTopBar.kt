package com.example.winterarc.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

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