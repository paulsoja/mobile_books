package com.spasinnya.mentoring.presentation.screens.homeflow.lessons

interface LessonsContract {
    data class State(
        val status: Status = Status.Init,
        val isLoading: Boolean = false,
        val lessons: List<String> = emptyList(),
    )

    sealed class Status {
        data object Init : Status()
        data object Loading : Status()
        data object Success : Status()
        data class Error(val type: ErrorType) : Status()
    }

    sealed class Event {

    }

    sealed class Effect {
        data object CheckPermissions: Effect()
        data class NavigateToScenes(val geofenceId: Int): Effect()
    }

    sealed class ErrorType {
        data object NoConnection : ErrorType()
    }
}