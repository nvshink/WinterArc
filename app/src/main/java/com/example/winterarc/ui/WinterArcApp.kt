package com.example.winterarc.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.example.winterarc.ui.utils.WinterArcContentType

@Composable
fun WinterArcApp (
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    val navigationType: NavigationSuiteType
    val contentType: WinterArcContentType
    
    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = NavigationSuiteType.NavigationBar
            contentType = WinterArcContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = NavigationSuiteType.NavigationRail
            contentType = WinterArcContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = NavigationSuiteType.NavigationDrawer
            contentType = WinterArcContentType.LIST_AND_DETAIL
        }
        else -> {
            navigationType = NavigationSuiteType.NavigationBar
            contentType = WinterArcContentType.LIST_ONLY
        }
    }
    WinterArcHomeScreen(navigationType =  navigationType, contentType =  contentType)
}

@Composable
fun WinterArcListItem (
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    image: ImageBitmap? = null,
    onCardClick: (() -> Unit)? = null,
    additionalInfo: @Composable (() -> Unit)? = null
) {
    Card (modifier = modifier.fillMaxSize().padding(5.dp), elevation = CardDefaults.cardElevation(1.dp)) {
        Row(
            modifier = Modifier.fillMaxSize().padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Column (Modifier) {
                Text(text = title, style = MaterialTheme.typography.labelMedium)
                Text(text = subtitle ?: "", style = MaterialTheme.typography.bodySmall)
            }
            if (additionalInfo != null) additionalInfo()
            if (image != null) {
                Image(image, contentDescription = null)
            }
        }
    }
}