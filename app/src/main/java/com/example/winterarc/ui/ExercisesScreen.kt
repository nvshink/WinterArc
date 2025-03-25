package com.example.winterarc.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.winterarc.data.model.Exercise
import com.example.winterarc.ui.utils.WinterArcContentType

@Composable
fun ExercisesScreen(
    modifier: Modifier = Modifier,
    exerciseUiState: ExerciseUiState,
    contentType: WinterArcContentType,
//    onTabPressed: (MailboxType) -> Unit,
    onExerciseCardPressed: (Exercise) -> Unit,
    onDetailScreenBackPressed: () -> Unit,
) {

    Box(modifier = modifier) {
        ListOnlyContent(
            exerciseUiState = uiState
        ) {

        }
    }
}



