package com.spasinnya.mentoring.presentation.screens.authflow.congrat

interface CongratContract {
    data class State(
        val state: ScreenType = ScreenType.Success,
        val isLoading: Boolean = false
    )

    sealed class Event {
        data object ButtonClicked : Event()
    }

    sealed class Effect {
        data object NavigateToMain : Effect()
    }

    enum class ScreenType {
        Success,
        Error
    }
}