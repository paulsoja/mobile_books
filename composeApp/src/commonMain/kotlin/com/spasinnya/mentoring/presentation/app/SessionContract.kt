package com.spasinnya.mentoring.presentation.app

import com.spasinnya.mentoring.domain.enums.Language
import com.spasinnya.mentoring.domain.model.AuthSteps
import com.spasinnya.mentoring.presentation.model.UiErrorType

interface SessionContract {
    data class State(
        val authStep: AuthSteps = AuthSteps.Init,
        val language: Language = Language.System,
        val error: UiErrorType = UiErrorType.None
    )
    sealed class Event {
        data class SetLanguage(val language: Language) : Event()
        data object Retry : Event()
    }

    sealed class Effect {

    }
}
