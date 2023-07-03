package com.example.mviexample.presentation.detail.intent

sealed interface DetailIntent {
    data class GetEntity(val id: Int?) : DetailIntent
    data class ChangeEntityDescription(val id: Int?, val description: String) : DetailIntent
}