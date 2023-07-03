package com.example.mviexample.presentation.detail.mapper

import com.example.mviexample.domain.Entity
import com.example.mviexample.presentation.detail.DetailEntity

class DetailEntityMapper {
    fun map(entity: Entity) = DetailEntity(entity.id, entity.name, entity.description)
}