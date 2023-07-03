package com.example.mviexample.domain

import kotlinx.coroutines.flow.Flow

interface EntityRepository {
    fun getAllEntities(): Flow<List<Entity>>
    fun getEntityById(id: Int): Entity?
    fun updateEntityDescription(id: Int, description: String)
}