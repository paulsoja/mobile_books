package com.spasinnya.mentoring.presentation.screens.authflow.register

import com.spasinnya.mentoring.domain.model.Email
import com.spasinnya.mentoring.domain.model.Password
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.DialogState
import com.spasinnya.mentoring.presentation.model.UiErrorType

interface RegisterContract {
    data class State(
        val email: Email = Email.init,
        val password: Password = Password.init,
        val isLoading: Boolean = false,
        val isPasswordVisible: Boolean = false,
        val dialog: DialogState<UiErrorType> = DialogState.Hidden,
        val emailError: Email.Error = Email.Error.NoError,
        val passwordError: Password.Error = Password.Error.NoError
    )

    sealed class Event {
        data class EmailChanged(val email: Email) : Event()
        data class PasswordChanged(val password: Password) : Event()
        data class PasswordVisibilityToggled(val isPasswordVisible: Boolean) : Event()
        data class ValidateCredentials(val email: Email, val password: Password) : Event()
        data object DismissDialog : Event()
    }

    sealed class Effect {
        data class NavigateToOtp(val email: String) : Effect()
    }
}