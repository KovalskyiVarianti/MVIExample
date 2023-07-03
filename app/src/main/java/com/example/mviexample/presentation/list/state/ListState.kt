package com.example.mviexample.presentation.list.state

import com.example.mviexample.presentation.list.EntityListItem

sealed interface ListState {
    object Loading : ListState
    data class Data(val entities: List<EntityListItem>) : ListState
}