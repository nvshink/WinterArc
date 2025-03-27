package com.example.winterarc.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.winterarc.data.model.Exercise
import com.example.winterarc.ui.utils.WinterArcContentType

@Composable
fun ExercisesScreen(
    modifier: Modifier = Modifier,
//    exerciseUiState: ExerciseUiState,
//    contentType: WinterArcContentType,
//    onExerciseCardPressed: (Exercise) -> Unit,
//    onExerciseItemScreenBackPressed: () -> Unit,
) {
    Box(modifier = modifier) {
        Text(text = "Exercise")
//        if (contentType == WinterArcContentType.LIST_AND_DETAIL) {
//            ExerciseListAndDetailContent(
//                exerciseUiState = exerciseUiState,
//                onExerciseCardPressed = onExerciseCardPressed,
//                onExerciseItemScreenBackPressed = onExerciseItemScreenBackPressed
//            )
//        } else {
//            ExerciseListOnlyContent(
//                exerciseUiState = exerciseUiState,
//                onExerciseCardPressed = onExerciseCardPressed,
//                onExerciseItemScreenBackPressed = onExerciseItemScreenBackPressed
//            )
//        }
    }
}



