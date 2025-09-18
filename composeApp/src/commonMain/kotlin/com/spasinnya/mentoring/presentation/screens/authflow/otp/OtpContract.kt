package com.spasinnya.mentoring.presentation.screens.authflow.otp

import com.spasinnya.mentoring.domain.model.Email
import com.spasinnya.mentoring.domain.model.OtpCode
import com.spasinnya.mentoring.domain.model.UiErrorType

interface OtpContract {
    data class State(
        val code: OtpCode = OtpCode.init,
        val email: Email = Email.init,
        val isLoading: Boolean = false,
        val showAlertDialog: Boolean = false,
        val messageError: UiErrorType? = null,
        val otpError: OtpCode.Error = OtpCode.Error.No_error
    )

    sealed class Event {
        data class CodeChanged(val code: String) : Event()
        data class ValidateOtpCredentials(val email: String, val code: String) : Event()
        data class HandleError(val errorType: ErrorType) : Event()
    }

    sealed class Effect {
        data object NavigateToCongratScreen : Effect()
    }

    sealed class ErrorType {
        data object NoError : ErrorType()
        data object NoConnection : ErrorType()
        data object UnexpectedError : ErrorType()
        data class EmailError(val message: Email.Error) : ErrorType()
        data class OtpCodeError(val message: OtpCode.Error) : ErrorType()
    }
}