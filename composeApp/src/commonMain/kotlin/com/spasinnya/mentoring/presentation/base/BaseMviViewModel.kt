package com.spasinnya.mentoring.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.mapper.toUiErrorType
import com.spasinnya.mentoring.domain.rules.DomainError
import com.spasinnya.mentoring.presentation.model.UiErrorType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseMviViewModel<State, Event, Effect>(
    initialState: State
) : ViewModel() {

    protected val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _effect = Channel<Effect>(Channel.BUFFERED)
    val effect: Flow<Effect> = _effect.receiveAsFlow()

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

    protected fun <S, Ev, Ef> BaseMviViewModel<S, Ev, Ef>.handleDomainErrors(
        errors: List<DomainError>,
        reduce: S.(UiErrorType) -> S
    ) {
        val error = (errors.firstOrNull() ?: DomainError.Unknown).toUiErrorType()
        setState { reduce(error) }
    }
}