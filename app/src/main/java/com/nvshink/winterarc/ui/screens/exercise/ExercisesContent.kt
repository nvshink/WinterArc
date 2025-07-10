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
import com.nvshink.winterarc.ui.states.exercise.ExerciseDetailUIState
import com.nvshink.winterarc.ui.states.exercise.ExerciseListUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WinterArcExerciseItemScreen(
    modifier: Modifier = Modifier,
    exerciseDetailUiState: ExerciseDetailUIState,
    onEditButtonClick: (() -> Unit)? = null,
    onDeleteButtonClick: (() -> Unit)? = null,
    onBackPressed: () -> Unit,
) {
    BackHandler {
        onBackPressed()
    }
    when(exerciseDetailUiState) {
        is ExerciseDetailUIState.LoadingState -> {

        }
        is ExerciseDetailUIState.ViewState -> {

        }
        is ExerciseDetailUIState.AddState -> {

        }
        is ExerciseDetailUIState.EditState -> {

        }
        is ExerciseDetailUIState.ErrorState -> {

        }
    }
}