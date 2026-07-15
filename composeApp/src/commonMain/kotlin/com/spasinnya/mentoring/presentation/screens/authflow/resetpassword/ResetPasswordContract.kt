package com.spasinnya.mentoring.presentation.screens.authflow.resetpassword

import com.spasinnya.mentoring.domain.model.Email
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.DialogState
import com.spasinnya.mentoring.presentation.model.UiErrorType

interface ResetPasswordContract {
    data class State(
        val email: Email = Email.init,
        val emailError: Email.Error = Email.Error.NoError,
        val isLoading: Boolean = false,
        val dialog: DialogState<UiErrorType> = DialogState.Hidden,
    )

    sealed class Event {
        data class EmailChanged(val email: Email) : Event()
        data object ValidateEmail : Event()
    }

    sealed class Effect {
        data class NavigateToOtp(val email: String) : Effect()
    }
}