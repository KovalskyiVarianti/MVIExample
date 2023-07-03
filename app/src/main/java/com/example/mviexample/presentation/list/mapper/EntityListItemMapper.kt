package com.example.mviexample.presentation.list.mapper

import com.example.mviexample.domain.Entity
import com.example.mviexample.presentation.list.EntityListItem

class EntityListItemMapper {
    fun map(entity: Entity) = EntityListItem(entity.id, entity.name, entity.description)
}