package com.nvshink.winterarc.ui.components.generic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.nvshink.winterarc.R

@Composable
fun WinterArcItemScreenTopBar(
    modifier: Modifier = Modifier,
    isBigScreen: Boolean,
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
                Icon(if (isBigScreen) Icons.Filled.Close else Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.close_button_icon_description))
            }
        }
        Row {
            if (actions != null) actions()
        }
    }
}