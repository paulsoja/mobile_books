package com.spasinnya.mentoring.presentation.screens.homeflow.home

import com.spasinnya.mentoring.domain.enums.Language

interface HomeContract {
    data class State(
        val status: Status = Status.Init,
        val isLoading: Boolean = false,
        val showSettingsDialog: Boolean = false,
        val selectedLanguage: Language = Language.UA
    )

    sealed class Status {
        data object Init : Status()
        data object Loading : Status()
        data object Success : Status()
        data class Error(val type: ErrorType) : Status()
    }

    sealed class Event {
        data class ToggleSettingsDialog(val show: Boolean) : Event()
        data class OnLanguageChosen(val language: Language) : Event()
    }

    sealed class Effect {

    }

    sealed class ErrorType {
        data object NoConnection : ErrorType()
    }
}