package com.spasinnya.mentoring.presentation.screens.homeflow.profile

interface ProfileContract {
    data class State(
        val firstName: String = "",
        val lastName: String = "",
        val email: String = "",
        val country: String = "Україна",
        val city: String = "Київ",
        val church: String = "",
        val isLoading: Boolean = false,
    )

    sealed class Event {
        data class UpdateFirstName(val value: String) : Event()
        data class UpdateLastName(val value: String) : Event()
        data class UpdateEmail(val value: String) : Event()
        data class UpdateCity(val value: String) : Event()
        data class UpdateChurch(val value: String) : Event()
    }

    sealed class Effect {
        data object NavigateToChangePassword : Effect()
    }
}
