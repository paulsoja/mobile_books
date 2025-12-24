package com.spasinnya.mentoring.presentation.screens.authflow.login

import com.spasinnya.mentoring.domain.model.Email
import com.spasinnya.mentoring.domain.model.Password
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.DialogState
import com.spasinnya.mentoring.presentation.model.UiErrorType

interface LoginContract {
    data class State(
        val email: Email = Email.init,
        val password: Password = Password.init,
        val isPasswordVisible: Boolean = false,
        val isLoading: Boolean = false,
        val dialog: DialogState<UiErrorType> = DialogState.Hidden,
        val emailError: Email.Error = Email.Error.NoError,
        val passwordError: Password.Error = Password.Error.NoError,
    )

    sealed class Event {
        data class EmailChanged(val email: Email) : Event()
        data class PasswordChanged(val password: Password) : Event()
        data class LoginClicked(val email: Email, val password: Password) : Event()
        data class PasswordVisibilityToggled(val isPasswordVisible: Boolean) : Event()
        data object DismissDialog : Event()
    }

    sealed class Effect {
        data object NavigateToMain : Effect()
    }
}