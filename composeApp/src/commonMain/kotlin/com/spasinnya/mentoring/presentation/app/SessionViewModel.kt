package com.spasinnya.mentoring.presentation.app

import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.enums.Language
import com.spasinnya.mentoring.domain.model.AuthSteps
import com.spasinnya.mentoring.domain.usecase.auth.CheckAuthStepsUseCase
import com.spasinnya.mentoring.domain.usecase.settings.GetAppLocaleUseCase
import com.spasinnya.mentoring.domain.usecase.settings.SetAppLocaleUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import com.spasinnya.mentoring.presentation.base.Validated
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SessionViewModel(
    private val checkAuthStepsUseCase: CheckAuthStepsUseCase,
    private val getAppLocaleUseCase: GetAppLocaleUseCase,
    private val setAppLocaleUseCase: SetAppLocaleUseCase
) : BaseMviViewModel<SessionContract.State, SessionContract.Event, SessionContract.Effect>(initialState = SessionContract.State()) {

    override fun handleEvent(event: SessionContract.Event) {
        when (event) {
            is SessionContract.Event.SetLanguage -> setAppLanguage(event.language)
        }
    }

    init {
        checkAuthSteps()
        getAppLanguage()
    }

    fun checkAuthSteps() = viewModelScope.launch {
        checkAuthStepsUseCase.invoke().collectLatest { result ->
            when (result) {
                is Validated.Invalid -> {
                    Napier.d { "SessionViewModel: checkAuthSteps: Invalid" }
                    setState { copy(authStep = AuthSteps.Auth) }
                }
                is Validated.Valid -> {
                    Napier.d { "SessionViewModel: checkAuthSteps: ${result.value}" }
                    setState { copy(authStep = result.value) }
                }
            }
        }
    }

    private fun getAppLanguage() = viewModelScope.launch {
        getAppLocaleUseCase.invoke().collectLatest { result ->
            when (result) {
                is Validated.Invalid -> Unit
                is Validated.Valid -> setState { copy(language = result.value) }
            }
        }
    }

    private fun setAppLanguage(language: Language) = viewModelScope.launch {
        setAppLocaleUseCase.invoke(language).collect()
    }
}