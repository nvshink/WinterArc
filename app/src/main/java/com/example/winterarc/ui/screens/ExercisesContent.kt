package com.example.winterarc.ui.screens

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
import com.example.winterarc.data.model.Exercise
import com.example.winterarc.ui.components.WinterArcItemScreenTopBar

@Composable
fun WinterArcExerciseItemScreen(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    exercise: Exercise?,
) {
    BackHandler {
        onBackPressed()
    }
    Column(modifier = modifier.fillMaxSize()) {
        WinterArcItemScreenTopBar(onBackButtonClicked = onBackPressed) {
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Edit, contentDescription = "")
            }
            IconButton(onClick = {}) {
                Icon(Icons.Filled.Delete, contentDescription = "")
            }
        }
        if (exercise == null) {
            Text(text = "No selected")
        } else {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                Text(
                    text = exercise.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 15.dp)
                )


            }
        }
    }
}