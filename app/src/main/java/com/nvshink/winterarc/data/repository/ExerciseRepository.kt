package com.nvshink.winterarc.data.repository

import android.content.Context
import android.net.Uri
import com.nvshink.winterarc.data.deleteImageFromLocalStorage
import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.data.room.ExerciseDao
import com.nvshink.winterarc.data.saveImageToLocalStorage
import kotlinx.coroutines.flow.Flow

class ExerciseRepository(
    private val dao: ExerciseDao
) {
    suspend fun upsertExercise(exercise: Exercise) = dao.upsertExercise(exercise = exercise)

    suspend fun deleteExercise(exercise: Exercise) = dao.deleteExercise(exercise = exercise)

    fun getExercisesByName(): Flow<MutableMap<Int, Exercise>> = dao.getExercisesByName()

    fun getExerciseWithTrainingPlanExercises() = dao.getExerciseWithTrainingPlanExercises()

    fun saveExerciseImageToLocalStorageAndGetUri(context: Context, uri: Uri): Uri? {
        return saveImageToLocalStorage(context, uri)
    }
    fun saveExerciseImagesListToLocalStorageAndGetList(context: Context, uriList: List<String>): List<String> {
        val savedUriSiringList: MutableList<String> = mutableListOf()
        uriList.forEach {  uriString: String ->
            savedUriSiringList.add(saveExerciseImageToLocalStorageAndGetUri(context, Uri.parse(uriString)).toString())
        }
        return savedUriSiringList
    }
    fun deleteExerciseImageFromLocalStorage(uri: Uri): Boolean {
        return deleteImageFromLocalStorage(uri)
    }
    fun deleteExerciseImagesListFromLocalStorage(uriList: List<String>): List<Boolean> {
        val savedUriSiringList: MutableList<Boolean> = mutableListOf()
        uriList.forEach {  uriString: String ->
            savedUriSiringList.add(deleteImageFromLocalStorage(Uri.parse(uriString)))
        }
        return savedUriSiringList
    }
}