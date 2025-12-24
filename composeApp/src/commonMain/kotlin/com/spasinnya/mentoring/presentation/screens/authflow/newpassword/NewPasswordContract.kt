package com.spasinnya.mentoring.presentation.screens.authflow.newpassword

import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.DialogState
import com.spasinnya.mentoring.presentation.model.UiErrorType

interface NewPasswordContract {
    data class State(
        val isLoading: Boolean = false,
        val dialog: DialogState<UiErrorType> = DialogState.Hidden,
    )

    sealed class Event {

    }

    sealed class Effect {

    }
}