package com.nvshink.data.local.trainingplanexercise.repository

import com.nvshink.data.local.trainingplanexercise.dao.TrainingPlanExerciseDao
import com.nvshink.data.local.utils.TrainingPlanExerciseMapper
import com.nvshink.domain.trainingplanexercise.model.TrainingPlanExerciseModel
import com.nvshink.domain.trainingplanexercise.repository.TrainingPlanExerciseRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.collections.map

class TrainingPlanExerciseRepositoryImpl @Inject constructor(
    private val trainingPlanExerciseDao: TrainingPlanExerciseDao
) : TrainingPlanExerciseRepository {
    /**
     * Insert an training plan exercise in Realm DB or update, if it has already been inserted.
     * @param trainingPlanExercise An training plan that is being recorded or updated in the DB.
     */
    override suspend fun upsertTrainingPlanExercise(trainingPlanExercise: TrainingPlanExerciseModel) =
        trainingPlanExerciseDao.upsertTrainingPlanExercise(
            trainingPlanExercise = TrainingPlanExerciseMapper.modelToEntity(
                trainingPlanExercise
            )
        )

    /**
     * Based on the training plan and the list of exercises and their parameters, a training plan is created and written to the DB.
     * @param trainingPlanExercises A list of training plan exercises.
     */
    override suspend fun upsertListOfTrainingPlanWithExercises(
        trainingPlanExercises: List<TrainingPlanExerciseModel>
    ) {
        trainingPlanExercises.forEach { trainingPlanExercise ->
            upsertTrainingPlanExercise(trainingPlanExercise)
        }
    }

    /**
     * Delete an training plan in Realm DB.
     * @param trainingPlanExercise An training plan exercise that is being deleted from the DB.
     */
    override suspend fun deleteTrainingPlanExercise(trainingPlanExercise: TrainingPlanExerciseModel) =
        trainingPlanExerciseDao.deleteTrainingPlanExercise(
            trainingPlanExercise = TrainingPlanExerciseMapper.modelToEntity(
                trainingPlanExercise
            )
        )

    /**
     * @return Flow with list training plan exercises.
     */
    override fun getTrainingPlanExercisesByTrainingPlanId(trainingPlanId: Long): Flow<List<TrainingPlanExerciseModel>> =
        trainingPlanExerciseDao.getTrainingPlanExercisesByTrainingPlanId(trainingPlanId)
            .map { trainingPlanExercisesEntity ->
                trainingPlanExercisesEntity.map { trainingPlanExerciseEntity ->
                    TrainingPlanExerciseMapper.entityToModel(trainingPlanExerciseEntity)
                }
            }

    /**
     * @return Flow with training plan exercise.
     */
    override fun getTrainingPlanExercisesById(id: Long): Flow<TrainingPlanExerciseModel> =
        trainingPlanExerciseDao.getTrainingPlanExercisesById(id).map { trainingPlanExerciseEntity ->
            TrainingPlanExerciseMapper.entityToModel(trainingPlanExerciseEntity)
        }
}