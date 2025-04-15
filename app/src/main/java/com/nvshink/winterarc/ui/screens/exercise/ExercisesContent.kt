package com.nvshink.winterarc.ui.screens.exercise

import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.ui.components.WinterArcItemScreenTopBar
import com.nvshink.winterarc.ui.event.ExerciseEvent
import com.nvshink.winterarc.ui.viewModel.ExerciseUiState
import kotlin.math.absoluteValue

@Composable
fun WinterArcExerciseItemScreen(
    modifier: Modifier = Modifier,
    exerciseUiState: ExerciseUiState,
    onEditButtonClick: (() -> Unit)?,
    onDeleteButtonClick: (() -> Unit)?,
    onBackPressed: () -> Unit,
    onEvent: (ExerciseEvent) -> Unit,
    exercise: Exercise,
) {
    val pagerState = rememberPagerState {
        exercise.images?.size ?: 0
    }
    BackHandler {
        onBackPressed()
    }
    Column(modifier = modifier.fillMaxSize()) {
        WinterArcItemScreenTopBar(onBackButtonClicked = onBackPressed) {
            if (onEditButtonClick != null){
                IconButton(onClick = onEditButtonClick) {
                    Icon(Icons.Filled.Edit, contentDescription = "")
                }
            }
            if (onDeleteButtonClick != null){
                IconButton(onClick = onDeleteButtonClick) {
                    Icon(Icons.Filled.Delete, contentDescription = "")
                }
            }
        }
        Column(modifier = Modifier.padding(20.dp)) {
            HorizontalPager(
                state = pagerState,
            ) { index ->
                Card(modifier = Modifier
                    .size(200.dp)
                    .graphicsLayer {
                        val pageOffset = (
                                (pagerState.currentPage - index) + pagerState
                                    .currentPageOffsetFraction
                                ).absoluteValue

                        // We animate the alpha, between 50% and 100%
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                ) {
                    AsyncImage(
                        model = exercise.images?.get(index),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

            }
            Text(
                text = exercise.name,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            Text(
                text = exercise.description ?: "",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 15.dp)
            )


        }
    }
}