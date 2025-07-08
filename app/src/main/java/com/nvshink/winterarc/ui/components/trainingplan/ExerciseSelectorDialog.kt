package com.nvshink.winterarc.ui.components.trainingplan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nvshink.domain.exercise.model.ExerciseModel
import com.nvshink.winterarc.R
import com.nvshink.winterarc.ui.components.generic.WinterArcDialog
import com.nvshink.winterarc.ui.components.generic.WinterArcListItem
import com.nvshink.winterarc.ui.components.generic.WinterArcListOfItems
import com.nvshink.winterarc.ui.screens.WinterArcEmptyItemScreenColors
import com.nvshink.winterarc.ui.states.exercise.ExerciseListUiState
import com.nvshink.winterarc.ui.states.TrainingPlanUiState
import com.nvshink.winterarc.ui.utils.WinterArcContentType

@Composable
fun ExerciseSelectorDialog(
    trainingPlanUiState: TrainingPlanUiState,
    exerciseListUiState: ExerciseListUiState,
    contentType: WinterArcContentType,
    onExerciseClick:(ExerciseModel) -> Unit,
    onBack: () -> Unit
) {
    WinterArcDialog(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = stringResource(R.string.close_button_icon_description),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        content = {
            when (exerciseListUiState) {
                is ExerciseListUiState.SuccessStateList -> {
                    WinterArcListOfItems(
                        modifier = Modifier.clip(MaterialTheme.shapes.extraLarge),
                        listOfItems = exerciseListUiState.exercisesMap, listItem = {
                            Row {
                                (
                                        WinterArcListItem(
                                            title = it.name,
                                            subtitle = null,
                                            onCardClick = { onExerciseClick(it) })
                                        )
                            }
                        },
                        listArrangement = 8.dp,
                        isLoading = exerciseListUiState::class == ExerciseListUiState.LoadingStateList::class,
                        listTopContent = {},
                        emptyListTitle = stringResource(R.string.empty_list_title_exercise),
                        emptyListIconDescription = stringResource(R.string.empty_list_icon_description_exercise),
                        emptyListIcon = Icons.Filled.SentimentVeryDissatisfied,
                        colors = WinterArcEmptyItemScreenColors(
                            iconTintColor = MaterialTheme.colorScheme.onSurface,
                            textColor = MaterialTheme.colorScheme.outline
                        ),
                        fab = {}
                    )
                }

                is ExerciseListUiState.LoadingStateList -> {
                    CircularProgressIndicator()
                }

                is ExerciseListUiState.ErrorStateList -> {
                    Text("Error")
                }
            }
        },
        contentType = contentType,
        onDismissRequest = onBack
    )
}