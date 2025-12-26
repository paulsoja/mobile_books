package com.spasinnya.mentoring.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.model.AuthSteps
import com.spasinnya.mentoring.domain.usecase.auth.AuthStepsUseCase
import com.spasinnya.mentoring.presentation.base.Validated
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SessionViewModel(
    private val checkAuthStepsUseCase: AuthStepsUseCase,
    private val handle: SavedStateHandle
) : ViewModel() {

    val _state = MutableStateFlow(AuthSteps.Init)
    val state: StateFlow<AuthSteps> = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = AuthSteps.Init
    )

    init {
        checkAuthSteps()
    }

    fun checkAuthSteps() = viewModelScope.launch {
        checkAuthStepsUseCase.invoke().collectLatest { result ->
            when (result) {
                is Validated.Invalid -> {
                    Napier.d { "checkAuthSteps: 1=${result.errors}" }
                    _state.update { AuthSteps.Auth }
                }
                is Validated.Valid -> {
                    Napier.d { "checkAuthSteps: 2=${result.value}" }
                    _state.update { result.value }
                }
            }
        }
    }
}