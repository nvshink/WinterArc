package com.nvshink.data.local.trainingplan.repository

import com.nvshink.data.local.trainingplan.dao.TrainingPlanDao
import com.nvshink.data.local.utils.ExerciseMapper
import com.nvshink.data.local.utils.TrainingPlanMapper
import com.nvshink.domain.trainingplan.model.TrainingPlanModel
import com.nvshink.domain.trainingplan.repository.TrainingPlanRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TrainingPlanRepositoryImpl @Inject constructor(
    private val dao: TrainingPlanDao,
) : TrainingPlanRepository {
    /**
     * Insert an training plan in Realm DB or update, if it has already been inserted.
     * @param trainingPlan An training plan that is being recorded or updated in the DB.
     */
    override suspend fun upsertTrainingPlan(trainingPlan: TrainingPlanModel): Long {
        return dao.upsertTrainingPlan(trainingPlan = TrainingPlanMapper.modelToEntity(trainingPlan))
    }

    /**
     * Delete an training plan in Realm DB.
     * @param trainingPlan An training plan that is being deleted from the DB.
     */
    override suspend fun deleteTrainingPlan(trainingPlan: TrainingPlanModel) =
        dao.deleteTrainingPlan(trainingPlan = TrainingPlanMapper.modelToEntity(trainingPlan))

    /**
     * @return Flow with list sorted by name in ascending order.
     */
    override fun getTrainingPlansByNameASC(): Flow<List<TrainingPlanModel>> =
        dao.getTrainingPlanByNameASC().map { trainingPlanEntities ->
            trainingPlanEntities.map { trainingPlanEntity ->
                TrainingPlanMapper.entityToModel(trainingPlanEntity)
            }
        }

    /**
     * @return Flow with list sorted by name in descending order.
     */
    override fun getTrainingPlansByNameDESC(): Flow<List<TrainingPlanModel>> =
        dao.getTrainingPlanByNameDESC().map { trainingPlanEntities ->
            trainingPlanEntities.map { trainingPlanEntity ->
                TrainingPlanMapper.entityToModel(trainingPlanEntity)
            }
        }



}
