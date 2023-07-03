package com.example.mviexample.data

import com.example.mviexample.domain.Entity
import com.example.mviexample.domain.EntityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MockEntityRepository(
    private val dataEntitySource: DataEntitySource = DataEntitySource,
    private val dataEntityMapper: DataEntityMapper = DataEntityMapper(),
) : EntityRepository {

    override fun getAllEntities(): Flow<List<Entity>> =
        dataEntitySource.getAllEntities().map { entities ->
            entities.map { dataEntityMapper.map(it) }
        }

    override fun getEntityById(id: Int): Entity? = dataEntitySource.getEntityById(id)?.let {
        dataEntityMapper.map(it)
    }

    override fun updateEntityDescription(id: Int, description: String) {
        dataEntitySource.updateEntityDescription(id, description)
    }
}