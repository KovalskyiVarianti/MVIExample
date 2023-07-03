package com.example.mviexample.presentation.list.intent

sealed interface ListIntent {
    object GetEntities : ListIntent
    data class ClickEntity(val id: Int) : ListIntent
}