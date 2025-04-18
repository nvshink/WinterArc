package com.nvshink.winterarc.ui.components.generic

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nvshink.winterarc.ui.event.ExerciseEvent
import com.nvshink.winterarc.ui.utils.WinterArcContentType

@Composable
fun <T> WinterArcListDetailRoute(
    modifier: Modifier = Modifier,
    isShowingList: Boolean,
    details: @Composable () -> Unit,
    listOfItems: MutableMap<Int, T>,
    listItem: @Composable (T) -> Unit,
    emptyListIcon: ImageVector? = null,
    emptyListIconDescription: String = "",
    emptyListTitle: String? = "",
    listArrangement: Dp = 0.dp,
    onEvent: (ExerciseEvent) -> Unit,
    contentType: WinterArcContentType = WinterArcContentType.LIST_ONLY,
    fab: (@Composable (Modifier) -> Unit)? = null
) {
    Box(modifier = modifier) {
        if (contentType == WinterArcContentType.LIST_ONLY) {
            if (isShowingList) {
                WinterArcListOfItems(
                    modifier = Modifier.fillMaxSize(),
                    listOfItems = listOfItems,
                    listItem = listItem,
                    emptyListIcon = emptyListIcon,
                    emptyListIconDescription = emptyListIconDescription,
                    emptyListTitle = emptyListTitle,
                    listArrangement = listArrangement,
                    fab = fab
                )
            } else {
                BackHandler { onEvent(ExerciseEvent.ShowList) }
            }
            WinterArcItemDetail(
                modifier = Modifier.alpha(if (isShowingList) 0f else 1f),
                details = details
            )
        } else {
            WinterArcListAndDetail(
                listOfItems = listOfItems,
                listItem = listItem,
                emptyListIcon = emptyListIcon,
                emptyListIconDescription = emptyListIconDescription,
                emptyListTitle = emptyListTitle,
                details = details,
                listArrangement = listArrangement,
                fab = fab
            )
            BackHandler { onEvent(ExerciseEvent.ShowList)  }
        }
    }
}

@Composable
fun <T> WinterArcListAndDetail(
    modifier: Modifier = Modifier,
    listOfItems: MutableMap<Int, T>,
    listItem: @Composable (T) -> Unit,
    emptyListIcon: ImageVector? = null,
    emptyListIconDescription: String = "",
    emptyListTitle: String? = "",
    listArrangement: Dp = 0.dp,
    details: @Composable () -> Unit,
    fab: (@Composable (Modifier) -> Unit)?
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        WinterArcListOfItems(
            modifier = Modifier.fillMaxHeight().weight(0.4f),
            listOfItems = listOfItems,
            listItem = listItem,
            emptyListIcon = emptyListIcon,
            emptyListIconDescription = emptyListIconDescription,
            emptyListTitle = emptyListTitle,
            listArrangement = listArrangement,
            fab = fab
        )
        WinterArcItemDetail(
            modifier = Modifier.weight(0.6f),
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