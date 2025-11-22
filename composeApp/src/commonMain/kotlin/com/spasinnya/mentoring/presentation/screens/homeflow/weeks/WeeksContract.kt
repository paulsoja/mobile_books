package com.spasinnya.mentoring.presentation.screens.homeflow.weeks

import com.spasinnya.mentoring.domain.model.Week

interface WeeksContract {
    data class State(
        val isLoading: Boolean = false,
        val weeks: List<Week> = emptyList(),
    )

    sealed class Event {
        data class LoadedWeeks(val weeks: List<Week>): Event()
        data class ShowLoading(val show: Boolean): Event()
    }

    sealed class Effect {
        data object CheckPermissions: Effect()
        data class NavigateToScenes(val geofenceId: Int): Effect()
    }

    sealed class ErrorType {
        data object NoConnection : ErrorType()
    }
}