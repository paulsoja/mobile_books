package com.spasinnya.mentoring.presentation.screens.authflow.newpassword

import com.spasinnya.mentoring.domain.model.Password
import com.spasinnya.mentoring.domain.model.PasswordConfirmation
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.DialogState
import com.spasinnya.mentoring.presentation.model.UiErrorType

interface NewPasswordContract {
    data class State(
        val password: Password = Password.init,
        val confirmation: PasswordConfirmation = PasswordConfirmation.init,
        val passwordError: Password.Error = Password.Error.NoError,
        val confirmationError: PasswordConfirmation.Error = PasswordConfirmation.Error.NoError,
        val isPasswordVisible: Boolean = false,
        val isConfirmationVisible: Boolean = false,
        val isLoading: Boolean = false,
        val dialog: DialogState<UiErrorType> = DialogState.Hidden,
    )

    sealed class Event {
        data class PasswordChanged(val password: Password) : Event()
        data class ConfirmationChanged(val confirmation: PasswordConfirmation) : Event()
        data class PasswordVisibilityToggled(val isVisible: Boolean) : Event()
        data class ConfirmationVisibilityToggled(val isVisible: Boolean) : Event()
        data object SaveClicked : Event()
        data object DismissDialog : Event()
    }

    sealed class Effect {
        data object NavigateToLogin : Effect()
    }
}
