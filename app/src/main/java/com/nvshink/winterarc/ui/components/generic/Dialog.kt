package com.nvshink.winterarc.ui.components.generic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.nvshink.winterarc.ui.utils.WinterArcContentType

@Composable
fun WinterArcDialog(
    topBar: @Composable () -> Unit,
    content: @Composable () -> Unit,
    contentType: WinterArcContentType,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = when(contentType) {
            WinterArcContentType.LIST_ONLY -> false
            WinterArcContentType.LIST_AND_DETAIL -> true
        })
    ) {
        Box(
            Modifier
                .clip(
                    when(contentType) {
                        WinterArcContentType.LIST_ONLY -> RoundedCornerShape(0.dp)
                        WinterArcContentType.LIST_AND_DETAIL -> MaterialTheme.shapes.extraLarge
                    }
                )
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Column( modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp)
            ) {
                topBar()
                content()
            }
        }
    }
}