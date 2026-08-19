package com.spasinnya.mentoring.presentation.screens.homeflow.profile

import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.DialogState
import com.spasinnya.mentoring.presentation.model.UiErrorType

interface ProfileContract {
    data class State(
        val firstName: String = "",
        val lastName: String = "",
        val email: String = "",
        val country: String = "Україна",
        val city: String = "Київ",
        val church: String = "",
        val isLoading: Boolean = false,
        val dialog: DialogState<UiErrorType> = DialogState.Hidden,
    )

    sealed class Event {
        data class UpdateFirstName(val value: String) : Event()
        data class UpdateLastName(val value: String) : Event()
        data object SaveProfile : Event()
        data object DismissDialog : Event()
    }

    sealed class Effect {
        data object NavigateToChangePassword : Effect()
        data object ProfileSaved : Effect()
        data object SaveFailed : Effect()
    }
}
