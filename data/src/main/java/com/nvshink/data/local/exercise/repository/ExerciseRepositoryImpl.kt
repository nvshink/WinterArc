package com.nvshink.data.local.exercise.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow
import androidx.core.net.toUri
import com.nvshink.data.local.exercise.dao.ExerciseDao
import com.nvshink.data.local.utils.ExerciseMapper
import com.nvshink.domain.exercise.model.ExerciseImageModel
import com.nvshink.domain.exercise.model.ExerciseModel
import com.nvshink.domain.exercise.repository.ExerciseRepository
import com.nvshink.domain.localstorage.LocalStorage
import com.nvshink.domain.resouce.Resource
import jakarta.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlin.collections.map

class ExerciseRepositoryImpl @Inject constructor(
    private val dao: ExerciseDao,
    private val localStorage: LocalStorage
) : ExerciseRepository {
    /**
     * Insert an exercise in Realm DB or update, if it has already been inserted.
     * @param exercise An exercise that is being recorded or updated in the DB.
     */
    override suspend fun upsertExercise(exercise: ExerciseModel) {
        deleteImagesList(exercise.imageLinks)
        dao.upsertExercise(
            exercise = ExerciseMapper.modelToEntity(
                exercise.copy(
                    imageLinks = saveImagesList(
                        imagesList = exercise.imageLinks
                    )
                )
            )
        )
    }

    /**
     * Delete an exercise in Realm DB.
     * @param exercise An exercise that is being deleted from the DB.
     */
    override suspend fun deleteExercise(exercise: ExerciseModel) {
        dao.deleteExercise(exercise = ExerciseMapper.modelToEntity(exercise))
        deleteImagesList(exercise.imageLinks)
    }

    /**
     * @return Flow with List sorted by name in ascending order.
     */
    override fun getExercisesByNameASC(): Flow<Resource<List<ExerciseModel>>> =
        dao.getExercisesByNameASC().map { exerciseEntities ->
            Resource.Success(exerciseEntities.map { exerciseEntity ->
                ExerciseMapper.entityToModel(exerciseEntity)
            })
        }
            .catch { e -> Resource.Error<ExerciseModel>(exception = e) }


    /**
     * @return Flow with List sorted by name in descending order.
     */
    override fun getExercisesByNameDESC(): Flow<Resource<List<ExerciseModel>>> =
        dao.getExercisesByNameDESC()
            .map { exerciseEntities ->
                Resource.Success(exerciseEntities.map { exerciseEntity ->
                    ExerciseMapper.entityToModel(exerciseEntity)
                })
            }
            .catch { e -> Resource.Error<ExerciseModel>(exception = e) }


    /**
     * @return Flow with exercise by id.
     */
    override fun getExerciseById(id: Long): Flow<Resource<ExerciseModel>> =
        dao.getExercisesById(id).map { exerciseEntity ->
            Resource.Success(ExerciseMapper.entityToModel(exerciseEntity), true)
        }
            .catch { e -> Resource.Error<ExerciseModel>(exception = e) }

    /**
     * Saves the photo to the local storage.
     * @param image The image that is being uploaded.
     * @return Saved image or null.
     */
    private suspend fun saveImage(image: ExerciseImageModel): ExerciseImageModel? {
        val fileName = "saved_image_${System.currentTimeMillis()}.jpg"
        return when {
            !image.isCashed && !image.isForRemoval -> {
                image.copy(
                    uriString = localStorage.saveImageToLocalStorage(
                        uriString = image.uriString,
                        fileName = fileName
                    ).toString()
                )
            }

            image.isCashed && !image.isForRemoval -> {
                image
            }

            else -> {
                null
            }
        }
    }

    /**
     * Saves the list of images to the local storage.
     * @param imagesList A list of images that will be that will be uploaded to cash.
     * @return Saved images mutable list of uris as a string.
     */
    private suspend fun saveImagesList(
        imagesList: List<ExerciseImageModel>
    ): List<ExerciseImageModel> {
        val savedUriSiringList: MutableList<ExerciseImageModel> = mutableListOf()
        imagesList.forEach {
            val savedImage: ExerciseImageModel? = saveImage(
                it
            )
            if (savedImage != null) savedUriSiringList.add(savedImage)
        }
        return savedUriSiringList
    }

    /**
     * Delete the photo from local storage.
     * @param image The image that is being deleted.
     * @return True if success.
     */
    private suspend fun deleteImage(image: ExerciseImageModel): Boolean {
        return if (image.isCashed && image.isForRemoval) {
            localStorage.deleteImageFromLocalStorage(image.uriString)
        } else false
    }

    /**
     * Deleted the list of images to the local storage.
     * @param imagesList A list of images that will be deleted from cash.
     * @return List of boolean for any deleted images. True if success.
     */
    private suspend fun deleteImagesList(imagesList: List<ExerciseImageModel>): List<Boolean> {
        val deletedUriSiringList: MutableList<Boolean> = mutableListOf()
        imagesList.forEach { image: ExerciseImageModel ->
            deletedUriSiringList.add(deleteImage(image))
        }
        return deletedUriSiringList
    }
}