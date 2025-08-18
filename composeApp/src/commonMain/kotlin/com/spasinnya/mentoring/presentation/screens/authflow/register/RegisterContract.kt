package com.spasinnya.mentoring.presentation.screens.authflow.register

import com.spasinnya.mentoring.domain.model.Email
import com.spasinnya.mentoring.domain.model.Password

interface RegisterContract {
    data class State(
        val email: Email = Email.init,
        val password: Password = Password.init,
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
        data class EmailChanged(val email: String) : Event()
        data class PasswordChanged(val password: String) : Event()
        data class ValidateCredentials(val email: String, val password: String) : Event()
    }

    sealed class Effect {

    }

    sealed class ErrorType {
        data object NoConnection : ErrorType()
        data object UnexpectedError : ErrorType()
        data class EmailError(val message: Email.Error) : ErrorType()
        data class PasswordError(val message: Password.Error) : ErrorType()
    }
}