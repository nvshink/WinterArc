package com.nvshink.winterarc.data.repository

import android.content.Context
import android.net.Uri
import com.nvshink.winterarc.data.utils.deleteImageFromLocalStorage
import com.nvshink.winterarc.data.model.Exercise
import com.nvshink.winterarc.data.room.ExerciseDao
import com.nvshink.winterarc.data.utils.saveImageToLocalStorage
import kotlinx.coroutines.flow.Flow
import androidx.core.net.toUri

class ExerciseRepository(
    private val dao: ExerciseDao
) {
    /**
     * Insert an exercise in Realm DB or update, if it has already been inserted.
     * @param exercise An exercise that is being recorded or updated in the DB.
     */
    suspend fun upsertExercise(exercise: Exercise) = dao.upsertExercise(exercise = exercise)

    /**
     * Delete an exercise in Realm DB.
     * @param exercise An exercise that is being deleted from the DB.
     */
    suspend fun deleteExercise(exercise: Exercise) = dao.deleteExercise(exercise = exercise)

    /**
     * @return Flow with mutable map sorted by name, there key is exercise id, value is exercise.
     */
    fun getExercisesByName(): Flow<MutableMap<Int, Exercise>> = dao.getExercisesByName()

    /**
     * Saves the photo to the local storage.
     * @param context Local context.
     * @param uri The uri of the image that is being uploaded.
     * @return Saved image uri or null.
     */
    fun saveImageByUri(context: Context, uri: Uri): Uri? {
        return saveImageToLocalStorage(context, uri)
    }
    /**
     * Saves the list of images to the local storage.
     * @param context Local context.
     * @param uriList A list of urls of images as a string that will be uploaded.
     * @return Saved images mutable list of uris as a string.
     */
    fun saveImagesList(context: Context, uriList: List<String>): List<String> {
        val savedUriSiringList: MutableList<String> = mutableListOf()
        uriList.forEach {  uriString: String ->
            savedUriSiringList.add(saveImageByUri(context,
                uriString.toUri()).toString())
        }
        return savedUriSiringList
    }
    /**
     * Delete the photo from local storage.
     * @param uri The uri of the image that is being deleted.
     * @return True if success.
     */
    fun deleteImage(uri: Uri): Boolean {
        return deleteImageFromLocalStorage(uri)
    }
    /**
     * Deleted the list of images to the local storage.
     * @param uriList A list of urls of images as a string that will be deleted.
     * @return List of boolean for any deleted images. True if success.
     */
    fun deleteImagesList(uriList: List<String>): List<Boolean> {
        val savedUriSiringList: MutableList<Boolean> = mutableListOf()
        uriList.forEach {  uriString: String ->
            savedUriSiringList.add(deleteImageFromLocalStorage(uriString.toUri()))
        }
        return savedUriSiringList
    }
}