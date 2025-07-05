package com.spasinnya.mentoring.presentation.screens.authflow.newpassword

interface NewPasswordContract {
    data class State(
        val status: Status = Status.Init,
        val isLoading: Boolean = false,
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

    }

    sealed class ErrorType {
        data object NoConnection : ErrorType()
    }
}