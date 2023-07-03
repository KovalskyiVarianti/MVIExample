package com.example.mviexample.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mviexample.data.MockEntityRepository
import com.example.mviexample.domain.EntityRepository
import com.example.mviexample.presentation.list.effect.ListEffect
import com.example.mviexample.presentation.list.intent.ListIntent
import com.example.mviexample.presentation.list.mapper.EntityListItemMapper
import com.example.mviexample.presentation.list.state.ListState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ListViewModel(
    private val entityRepository: EntityRepository = MockEntityRepository(),
    private val entityListItemMapper: EntityListItemMapper = EntityListItemMapper(),
) : ViewModel() {

    private val state = MutableStateFlow<ListState>(ListState.Loading)
    fun getState() = state.asStateFlow()

    private val effectChannel = Channel<ListEffect>()
    fun getEffect() = effectChannel.receiveAsFlow()

    private val intentChannel = Channel<ListIntent>(Channel.UNLIMITED)

    init {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect(::handleIntent)
        }
    }

    fun sendIntent(intent: ListIntent) {
        viewModelScope.launch {
            intentChannel.send(intent)
        }
    }

    private fun handleIntent(intent: ListIntent) {
        when (intent) {
            ListIntent.GetEntities -> {
                viewModelScope.launch {
                    entityRepository.getAllEntities().map { entities ->
                        entities.map { entityListItemMapper.map(it) }
                    }.onEach {
                        state.value = ListState.Data(it)
                    }.collect()
                }
            }
            is ListIntent.ClickEntity -> {
                sendEffect(ListEffect.NavigateToDetail(intent.id))
            }
        }
    }

    private fun sendEffect(effect: ListEffect) {
        viewModelScope.launch {
            effectChannel.send(effect)
        }
    }
}