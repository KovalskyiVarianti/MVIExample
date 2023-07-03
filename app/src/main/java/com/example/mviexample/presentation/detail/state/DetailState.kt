package com.example.mviexample.presentation.detail.state

import com.example.mviexample.presentation.detail.DetailEntity

sealed interface DetailState {
    object Loading : DetailState
    data class Data(val entity: DetailEntity) : DetailState
}