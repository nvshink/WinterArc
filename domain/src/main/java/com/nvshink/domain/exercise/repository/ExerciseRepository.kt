package com.nvshink.domain.exercise.repository

import com.nvshink.domain.exercise.model.ExerciseModel

interface ExerciseRepository {
    /**
     * Insert an exercise in Realm DB or update, if it has already been inserted.
     * @param exercise An exercise that is being recorded or updated in the DB.
     */
    suspend fun upsertExercise(exercise: ExerciseModel)

    /**
     * Delete an exercise in Realm DB.
     * @param exercise An exercise that is being deleted from the DB.
     */
    suspend fun deleteExercise(exercise: ExerciseModel)

    /**
     * @return Flow with List sorted by name in ascending order.
     */
    fun getExercisesByNameASC(): Flow<List<ExerciseModel>>

    /**
     * @return Flow with List sorted by name in descending order.
     */
    fun getExercisesByNameDESC(): Flow<List<Exercise>> = dao.getExercisesByNameDESC()

    /**
     * @return Flow with exercise by id.
     */
    fun getExercisesById(id: Long): Flow<Exercise> = dao.getExercisesById(id)

    /**
     * Delete the photo from local storage.
     * @param uri The uri of the image that is being deleted.
     * @return True if success.
     */
    fun deleteImage(uri: Uri): Boolean {
        return com.nvshink.domain.utils.deleteImageFromLocalStorage(uri)
    }
    /**
     * Deleted the list of images to the local storage.
     * @param uriList A list of urls of images as a string that will be deleted.
     * @return List of boolean for any deleted images. True if success.
     */
    fun deleteImagesList(uriList: List<String>): List<Boolean> {
        val savedUriSiringList: MutableList<Boolean> = mutableListOf()
        uriList.forEach {  uriString: String ->
            savedUriSiringList.add(com.nvshink.domain.utils.deleteImageFromLocalStorage(uriString.toUri()))
        }
        return savedUriSiringList
    }
}