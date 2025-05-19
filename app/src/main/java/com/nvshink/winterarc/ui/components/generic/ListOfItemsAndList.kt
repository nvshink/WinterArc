package com.nvshink.winterarc.ui.components.generic

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun <T> WinterArcListOfItems(
    modifier: Modifier = Modifier,
    listOfItems: Map<Long, T>,
    listItem: @Composable (T) -> Unit,
    emptyListIcon: ImageVector? = null,
    emptyListIconDescription: String = "",
    emptyListTitle: String? = "",
    listArrangement: Dp = 0.dp,
    isLoading: Boolean,
    listTopContent: (@Composable () -> Unit),
    fab: (@Composable (Modifier) -> Unit)?
) {
    if (isLoading) {
        CircularProgressIndicator()
    } else {
        Box(
            modifier = modifier
                .padding(16.dp)
        ) {
            if (listOfItems.isNotEmpty()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(listArrangement)
                ) {
                    item {
                        listTopContent()
                    }
                    items(listOfItems.toList()) { item ->
                        listItem(item.second)
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (emptyListIcon != null) Icon(
                        imageVector = emptyListIcon,
                        contentDescription = emptyListIconDescription,
                        modifier = Modifier.size(64.dp)
                    )
                    if (emptyListTitle != null) Text(
                        text = emptyListTitle,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(300.dp)
                            .padding(top = 20.dp)
                    )
                }
            }
            if (fab != null) {
                fab(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                )
            }
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