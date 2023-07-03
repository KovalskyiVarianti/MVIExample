package com.example.mviexample.presentation.list.effect

sealed interface ListEffect {
    data class NavigateToDetail(val id: Int) : ListEffect
}