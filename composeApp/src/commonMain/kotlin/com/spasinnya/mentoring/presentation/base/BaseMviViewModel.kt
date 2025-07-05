package com.spasinnya.mentoring.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseMviViewModel<State, Event, Effect> : ViewModel() {

    private val _state = MutableStateFlow(createInitialState())
    protected val state: StateFlow<State> = _state.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = createInitialState()
        )

    private val _effect = Channel<Effect>(Channel.BUFFERED)
    protected val effect = _effect.receiveAsFlow()

    protected abstract fun createInitialState(): State
    protected abstract fun handleEvent(event: Event)

    fun dispatchEvent(event: Event) {
        handleEvent(event)
    }

    protected fun setState(reducer: State.() -> State) {
        _state.update { it.reducer() }
    }

    protected fun sendEffect(builder: () -> Effect) {
        viewModelScope.launch {
            _effect.send(builder())
        }
    }
}