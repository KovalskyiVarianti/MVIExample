package com.example.mviexample.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

object DataEntitySource {

    private var entities = MutableStateFlow(
        mutableListOf(
            DataEntity(
                1,
                "Apple",
                "A fruit with a crunchy texture and a sweet or tart flavor."
            ),
            DataEntity(
                2,
                "Car",
                "A wheeled motor vehicle used for transportation."
            ),
            DataEntity(
                3,
                "Book",
                "A written or printed work consisting of pages glued or sewn together along one side and bound in covers."
            )
        )
    )

    fun getAllEntities(): Flow<List<DataEntity>> = entities

    fun getEntityById(id: Int): DataEntity? = entities.value.find { it.id == id }

    fun updateEntityDescription(id: Int, description: String) {
        entities.value = entities.value.map {
            if (it.id == id) {
                it.copy(description = description)
            } else {
                it
            }
        }.toMutableList()
    }
}