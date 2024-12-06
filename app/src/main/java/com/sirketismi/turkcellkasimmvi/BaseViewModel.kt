package com.sirketismi.turkcellkasimmvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface ViewEvent

interface ViewState

interface ViewSideEffect

abstract class BaseViewModel<Event : ViewEvent, UiState : ViewState, Effect : ViewSideEffect>: ViewModel() {

    private val initialState : UiState by lazy { setInitialState() }
    abstract fun setInitialState(): UiState
    abstract fun handleEvents(event: Event)

    private val _viewState : MutableStateFlow<UiState> = MutableStateFlow(initialState)
    val viewState : StateFlow<UiState> = _viewState

    private val _event : MutableSharedFlow<Event> = MutableSharedFlow()

    private val _effect : Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribesToEvents()
    }

    private fun subscribesToEvents() {
        viewModelScope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }

    fun setEffect(builder : ()->Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    fun setState(reducer : UiState.() -> UiState) {
        val newState = viewState.value.reducer()
        _viewState.value = newState
    }



    // MutableStateFlow
    // MutableSharedFlow
    // Channel

    fun setEvent(event : Event) {
        viewModelScope.launch { _event.emit(event) }
    }

}



