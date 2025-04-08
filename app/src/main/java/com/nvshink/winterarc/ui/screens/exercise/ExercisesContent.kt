package com.nvshink.winterarc.ui.screens.exercise

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.ui.components.WinterArcItemScreenTopBar

@Composable
fun WinterArcExerciseItemScreen(
    modifier: Modifier = Modifier,
    onEditButtonClick: (() -> Unit)?,
    onDeleteButtonClick: (() -> Unit)?,
    onBackPressed: () -> Unit,
    exercise: Exercise,
) {
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
//                Image(
//                    bitmap = exercise.
//                )
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