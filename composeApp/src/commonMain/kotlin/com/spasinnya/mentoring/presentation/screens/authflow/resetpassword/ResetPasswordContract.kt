package com.spasinnya.mentoring.presentation.screens.authflow.resetpassword

import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.DialogState
import com.spasinnya.mentoring.presentation.model.UiErrorType

interface ResetPasswordContract {
    data class State(
        val isLoading: Boolean = false,
        val dialog: DialogState<UiErrorType> = DialogState.Hidden,
    )

    sealed class Event {

    }

    sealed class Effect {

    }
}