package com.spasinnya.mentoring.presentation.screens.authflow.register

import com.spasinnya.mentoring.domain.model.Email
import com.spasinnya.mentoring.domain.model.Password
import com.spasinnya.mentoring.domain.model.UiErrorType

interface RegisterContract {
    data class State(
        val email: Email = Email.init,
        val password: Password = Password.init,
        val isLoading: Boolean = false,
        val showAlertDialog: Boolean = false,
        val messageError: UiErrorType? = null,
        val emailError: Email.Error = Email.Error.No_error,
        val passwordError: Password.Error = Password.Error.No_error
    )

    sealed class Event {
        data class EmailChanged(val email: String) : Event()
        data class PasswordChanged(val password: String) : Event()
        data class ValidateCredentials(val email: String, val password: String) : Event()
        data class HandleError(val errorType: ErrorType) : Event()
    }

    sealed class Effect {
        data class NavigateToOtp(val email: String) : Effect()
    }

    sealed class ErrorType {
        data object NoError : ErrorType()
        data object NoConnection : ErrorType()
        data object UnexpectedError : ErrorType()
        data object UserAlreadyExists : ErrorType()
        data class EmailError(val message: Email.Error) : ErrorType()
        data class PasswordError(val message: Password.Error) : ErrorType()
    }
}