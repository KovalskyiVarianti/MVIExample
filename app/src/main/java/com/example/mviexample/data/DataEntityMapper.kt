package com.example.mviexample.data

import com.example.mviexample.domain.Entity

class DataEntityMapper {
    fun map(dataEntity: DataEntity) =
        Entity(dataEntity.id, dataEntity.name, dataEntity.description)
}