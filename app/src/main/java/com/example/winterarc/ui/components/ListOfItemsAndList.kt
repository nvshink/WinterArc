package com.example.winterarc.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun <T> WinterArcListOfItems(
    modifier: Modifier = Modifier,
    listOfItems: MutableMap<Int, T>,
    listItem: @Composable (T) -> Unit,
    listArrangement: Dp = 0.dp
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(listArrangement)) {
        items(listOfItems.toList()) { item ->
            listItem(item.second)
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