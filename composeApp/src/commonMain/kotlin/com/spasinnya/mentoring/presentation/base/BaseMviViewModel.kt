package com.spasinnya.mentoring.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.data.model.AppError
import io.github.aakira.napier.Napier
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
    val state: StateFlow<State> = _state.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = createInitialState()
        )

    private val _effect = Channel<Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

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

    protected fun handleFailures(t: Throwable) {
        when (t) {
            is AppError.NoConnection -> handleNetworkError()
            is AppError.Domain -> {
                Napier.d("handleFailures: message=${t.message}")
                Napier.d("handleFailures: statusCode=${t.statusCode}")
                Napier.d("handleFailures: code=${t.code}")
                handleDomainError(t.statusCode, t.code)
            }
            is AppError.Unexpected -> handleUnexpectedError(t)
            else -> handleUnexpectedError(t)
        }
    }

    protected open fun handleDomainError(statusCode: String, code: Int) {
        Napier.d("handleFailures: domainError=$statusCode")
    }

    protected open fun handleNetworkError() {
        Napier.d("handleFailures: networkError")
    }

    protected open fun handleUnexpectedError(throwable: Throwable) {
        Napier.d("handleFailures: unexpectedError=$throwable")
    }
}