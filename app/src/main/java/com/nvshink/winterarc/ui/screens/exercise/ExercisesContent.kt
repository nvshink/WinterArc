package com.nvshink.winterarc.ui.screens.exercise

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nvshink.winterarc.R
import com.nvshink.winterarc.ui.components.generic.WinterArcItemScreenTopBar
import com.nvshink.winterarc.ui.viewModel.ExerciseUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WinterArcExerciseItemScreen(
    modifier: Modifier = Modifier,
    exerciseUiState: ExerciseUiState,
    onEditButtonClick: (() -> Unit)? = null,
    onDeleteButtonClick: (() -> Unit)? = null,
    onBackPressed: () -> Unit,
) {
    BackHandler {
        onBackPressed()
    }
    if (exerciseUiState.currentExercise != null) { //TODO create handler empty current exercise navigate
        val carouselState =
            rememberCarouselState { exerciseUiState.currentExercise.images.size }
        Column(modifier = modifier.fillMaxSize()) {
            WinterArcItemScreenTopBar(modifier = Modifier.padding(16.dp), isBigScreen = exerciseUiState.isBigScreen, onBackButtonClicked = onBackPressed) {
                if (onEditButtonClick != null) {
                    IconButton(onClick = onEditButtonClick) {
                        Icon(Icons.Filled.Edit, contentDescription = stringResource(R.string.edit_exercise_button_icon_description))
                    }
                }
                if (onDeleteButtonClick != null) {
                    IconButton(onClick = onDeleteButtonClick) {
                        Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.delete_button_icon_description))
                    }
                }
            }
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = exerciseUiState.currentExercise.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                if (exerciseUiState.currentExercise.images.isNotEmpty()){
                    HorizontalMultiBrowseCarousel(
                        state = carouselState,
                        preferredItemWidth = 300.dp,    //TODO Fix single image width
                        itemSpacing = 8.dp,
                        contentPadding = (PaddingValues(horizontal = 16.dp, vertical = 8.dp)),
                        minSmallItemWidth = 40.dp,
                        maxSmallItemWidth = 56.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(height = 300.dp)
                    ) { itemIndex ->
                        AsyncImage(
                            model = exerciseUiState.currentExercise.images[itemIndex],
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .maskClip(MaterialTheme.shapes.extraLarge)
                                .background(MaterialTheme.colorScheme.surface),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                Text(
                    text = exerciseUiState.currentExercise.description,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }

        }
    }
}