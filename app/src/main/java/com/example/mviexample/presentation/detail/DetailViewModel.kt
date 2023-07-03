package com.example.mviexample.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mviexample.data.MockEntityRepository
import com.example.mviexample.domain.EntityRepository
import com.example.mviexample.presentation.detail.effect.DetailEffect
import com.example.mviexample.presentation.detail.intent.DetailIntent
import com.example.mviexample.presentation.detail.mapper.DetailEntityMapper
import com.example.mviexample.presentation.detail.state.DetailState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val entityRepository: EntityRepository = MockEntityRepository(),
    private val detailEntityMapper: DetailEntityMapper = DetailEntityMapper(),
) : ViewModel() {

    private val state = MutableStateFlow<DetailState>(DetailState.Loading)
    fun getState() = state.asStateFlow()

    private val effectChannel = Channel<DetailEffect>(Channel.UNLIMITED)
    fun getEffect() = effectChannel.receiveAsFlow()

    private val intentChannel = Channel<DetailIntent>(Channel.UNLIMITED)


    init {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect(::handleIntent)
        }
    }

    fun sendIntent(intent: DetailIntent) {
        viewModelScope.launch {
            intentChannel.send(intent)
        }
    }

    private fun handleIntent(intent: DetailIntent) {
        when (intent) {
            is DetailIntent.GetEntity -> {
                intent.id?.let { id ->
                    entityRepository.getEntityById(id)?.let { entity ->
                        state.value = DetailState.Data(detailEntityMapper.map(entity))
                    }
                } ?: sendEffect(
                    DetailEffect.Error("Some error")
                )
            }
            is DetailIntent.ChangeEntityDescription -> {
                intent.id?.let { id ->
                    entityRepository.updateEntityDescription(id, intent.description)
                    (state.value as? DetailState.Data)?.let { previousState ->
                        val entity = previousState.entity.copy(description = intent.description)
                        state.value = previousState.copy(entity = entity)
                    }
                }
            }
        }
    }

    private fun sendEffect(effect: DetailEffect) {
        viewModelScope.launch {
            effectChannel.send(effect)
        }
    }
}