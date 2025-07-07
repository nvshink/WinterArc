package com.nvshink.domain.trainingplan.repository

import com.nvshink.domain.trainingplan.model.TrainingPlanModel
import kotlinx.coroutines.flow.Flow

interface TrainingPlanRepository {
    /**
     * Insert an training plan in Realm DB or update, if it has already been inserted.
     * @param trainingPlan An training plan that is being recorded or updated in the DB.
     */
    suspend fun upsertTrainingPlan(trainingPlan: TrainingPlanModel): Long

    /**
     * Delete an training plan in Realm DB.
     * @param trainingPlan An training plan that is being deleted from the DB.
     */
    suspend fun deleteTrainingPlan(trainingPlan: TrainingPlanModel)

    /**
     * @return Flow with list sorted by name in ascending order.
     */
    fun getTrainingPlansByNameASC(): Flow<List<TrainingPlanModel>>

    /**
     * @return Flow with list sorted by name in descending order.
     */
    fun getTrainingPlansByNameDESC(): Flow<List<TrainingPlanModel>>

}