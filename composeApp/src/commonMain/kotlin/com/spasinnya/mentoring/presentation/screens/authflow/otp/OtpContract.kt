package com.spasinnya.mentoring.presentation.screens.authflow.otp

import com.spasinnya.mentoring.domain.model.Email
import com.spasinnya.mentoring.domain.model.OtpCode
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.DialogState
import com.spasinnya.mentoring.presentation.model.UiErrorType

interface OtpContract {
    data class State(
        val email: Email.Valid,
        val otp: OtpCode = OtpCode.init,
        val otpError: OtpCode.Error = OtpCode.Error.NoError,
        val isLoading: Boolean = false,
        val dialog: DialogState<UiErrorType> = DialogState.Hidden,
    )

    sealed class Event {
        data class OtpChanged(val otp: OtpCode) : Event()
        data class ConfirmClicked(val email: Email.Valid, val otp: OtpCode) : Event()
        data object DismissDialog : Event()
    }

    sealed class Effect {
        data object NavigateToCongratScreen : Effect()
    }
}