package com.example.mviexample.presentation.detail.effect

sealed interface DetailEffect {
    data class Error(val message: String): DetailEffect
}