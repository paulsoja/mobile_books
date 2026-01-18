package com.spasinnya.mentoring.presentation.app

import com.spasinnya.mentoring.domain.enums.Language
import com.spasinnya.mentoring.domain.model.AuthSteps

interface SessionContract {
    data class State(
        val authStep: AuthSteps = AuthSteps.Init,
        val language: Language = Language.System
    )
    sealed class Event {
        data class SetLanguage(val language: Language) : Event()
    }

    sealed class Effect {

    }
}