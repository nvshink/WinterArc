package com.example.winterarc.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.winterarc.ui.utils.WinterArcContentType

@Composable
fun <T> WinterArcListDetailRoute(
    modifier: Modifier = Modifier,
    isShowingList: Boolean,
    details: @Composable () -> Unit,
    listOfItems: MutableMap<Int, T>,
    listItem: @Composable (T) -> Unit,
    listArrangement: Dp = 0.dp,
    contentType: WinterArcContentType = WinterArcContentType.LIST_ONLY,
    onItemScreenBackPressed: () -> Unit,
) {
    Box(modifier = modifier) {
        if (contentType == WinterArcContentType.LIST_ONLY) {
            if (isShowingList) {
                WinterArcListOfItems(
                    listOfItems = listOfItems,
                    listItem = listItem,
                    listArrangement = listArrangement
                )
            } else {
                BackHandler { onItemScreenBackPressed() }
            }
            WinterArcItemDetail(
                modifier = Modifier.alpha(if (isShowingList) 0f else 1f),
                details = details
            )
        } else {
            WinterArcListAndDetail(
                listOfItems = listOfItems,
                listItem = listItem,
                details = details,
                listArrangement = listArrangement
            )
            BackHandler { onItemScreenBackPressed() }
        }
    }
}

@Composable
fun <T> WinterArcListAndDetail(
    modifier: Modifier = Modifier,
    listOfItems: MutableMap<Int, T>,
    listItem: @Composable (T) -> Unit,
    listArrangement: Dp = 0.dp,
    details: @Composable () -> Unit,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        WinterArcListOfItems(
            modifier = Modifier.weight(1f),
            listOfItems = listOfItems,
            listItem = listItem,
            listArrangement = listArrangement
        )
        WinterArcItemDetail(
            modifier = Modifier.weight(1f),
            details = details
        )
    }
}

@Composable
fun WinterArcItemDetail(
    modifier: Modifier = Modifier,
    details: @Composable () -> Unit
) {
    Box(modifier = modifier) {
        details()
    }
}