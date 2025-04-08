package com.nvshink.winterarc.ui.viewModel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.data.model.InternalStoragePhoto
import com.nvshink.winterarc.data.repository.ExerciseRepository
import com.nvshink.winterarc.ui.event.ExerciseEvent
import com.nvshink.winterarc.ui.utils.SortTypes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
open class ExerciseViewModel @Inject constructor(
    private val repository: ExerciseRepository
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortTypes.NAME)

    private val _exercises = _sortType
        .flatMapLatest { sortType ->
            when (sortType) {
                SortTypes.NAME -> repository.getExercisesByName()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), mutableMapOf())

    private val _uiState = MutableStateFlow(ExerciseUiState())

    val uiState = combine(_uiState, _sortType, _exercises) { uiState, sortType, exercises ->
        uiState.copy(
            exercisesMap = exercises,
            sortType = sortType,
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ExerciseUiState())

    fun resetExercisesListItemState() {
        _uiState.update {
            it.copy(
                currentExercise = null,
                isShowingList = true
            )
        }
    }

    fun onEvent(event: ExerciseEvent) {
        when (event) {
            is ExerciseEvent.DeleteExercise -> {
                viewModelScope.launch {
                    repository.deleteExercise(event.exercise)
                }
            }

            is ExerciseEvent.SaveExercise -> {
                val exercise: Exercise = uiState.value.currentExercise ?: Exercise(
                    name = uiState.value.name,
                    images = uiState.value.images,
                    description = uiState.value.description
                )
                viewModelScope.launch {
                    repository.upsertExercise(exercise)
                }
            }

            is ExerciseEvent.UpdateCurrentExercise -> {
                _uiState.update {
                    it.copy(
                        currentExercise = event.exercise,
                        name = event.exercise.name,
                        description = event.exercise.description ?: "",
                        images = event.exercise.images ?: emptyList()
                    )
                }
            }

            is ExerciseEvent.SetName -> {
                _uiState.update {
                    it.copy(
                        name = event.name
                    )
                }
            }

            is ExerciseEvent.SetDescription -> {
                _uiState.update {
                    it.copy(
                        description = event.description
                    )
                }
            }

            is ExerciseEvent.SetImages -> {
                _uiState.update {
                    it.copy(
                        images = event.images
                    )
                }
            }

            is ExerciseEvent.HideDialog -> {
                _uiState.update {
                    it.copy(
                        isAddingDialog = false
                    )
                }
            }

            is ExerciseEvent.ShowDialog -> {
                _uiState.update {
                    it.copy(
                        isAddingDialog = true
                    )
                }
            }

            is ExerciseEvent.SortExercises -> {
                _sortType.value = event.sortType
            }

            ExerciseEvent.HideList -> {
                _uiState.update {
                    it.copy(
                        isShowingList = false
                    )
                }
            }

            ExerciseEvent.ShowList -> {
                _uiState.update {
                    it.copy(
                        isShowingList = true
                    )
                }
            }
        }
    }


    private suspend fun deletePhotoToInternalStorage(context: Context, filename: String): Boolean {
        return try {
            context.deleteFile(filename)
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    private suspend fun loadPhotosFromInternalStorage(context: Context): List<InternalStoragePhoto> {
        return withContext(Dispatchers.IO) {
            val files = context.filesDir.listFiles()
            files?.filter {
                it.canRead() && it.isFile && it.name.endsWith(".jpg")
            }?.map {
                val bytes = it.readBytes()
                val bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                InternalStoragePhoto(it.name, bmp)
            } ?: listOf()
        }
    }

    private suspend fun savePhotoToInternalStorage(
        context: Context,
        filename: String,
        fileFormat: String,
        bmp: Bitmap
    ): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                context.openFileOutput("$filename.$fileFormat", Context.MODE_PRIVATE).use {
                    if (!bmp.compress(Bitmap.CompressFormat.JPEG, 95, it)) {
                        throw IOException("Couldn't save bitmap.")
                    }
                }
                true
            }
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

}

data class ExerciseUiState(
    val exercisesMap: MutableMap<Int, Exercise> = mutableMapOf(),
    val currentExercise: Exercise? = null,
    val name: String = "",
    val description: String = "",
    val images: List<String> = emptyList(),
    val isShowingList: Boolean = true,
    val isAddingDialog: Boolean = false,
    val sortType: SortTypes = SortTypes.NAME
)