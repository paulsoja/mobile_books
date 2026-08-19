package com.spasinnya.mentoring.presentation.app

import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.enums.Language
import com.spasinnya.mentoring.domain.usecase.auth.ObserveAuthStepsUseCase
import com.spasinnya.mentoring.domain.usecase.settings.GetAppLocaleUseCase
import com.spasinnya.mentoring.domain.usecase.settings.SetAppLocaleUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import com.spasinnya.mentoring.presentation.base.Validated
import com.spasinnya.mentoring.presentation.model.UiErrorType
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SessionViewModel(
    private val observeAuthStepsUseCase: ObserveAuthStepsUseCase,
    private val getAppLocaleUseCase: GetAppLocaleUseCase,
    private val setAppLocaleUseCase: SetAppLocaleUseCase
) : BaseMviViewModel<SessionContract.State, SessionContract.Event, SessionContract.Effect>(initialState = SessionContract.State()) {

    private var authStepsJob: Job? = null

    override fun handleEvent(event: SessionContract.Event) {
        when (event) {
            is SessionContract.Event.SetLanguage -> setAppLanguage(event.language)
            SessionContract.Event.Retry -> checkAuthSteps()
        }
    }

    init {
        checkAuthSteps()
        getAppLanguage()
    }

    fun checkAuthSteps() {
        authStepsJob?.cancel()
        authStepsJob = viewModelScope.launch {
            setState { copy(error = UiErrorType.None) }

            observeAuthStepsUseCase.invoke().collectLatest { result ->
                when (result) {
                    is Validated.Invalid -> {
                        Napier.d { "SessionViewModel: checkAuthSteps: ${result.error}" }
                        handleDomainErrors(
                            error = result.error,
                            reduce = { errorType -> copy(error = errorType) }
                        )
                    }
                    is Validated.Valid -> {
                        Napier.d { "SessionViewModel: checkAuthSteps: ${result.value}" }
                        setState { copy(authStep = result.value, error = UiErrorType.None) }
                    }
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
