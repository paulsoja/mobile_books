package com.spasinnya.mentoring.presentation.screens.homeflow.profile

import androidx.lifecycle.SavedStateHandle
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel

class ProfileViewModel(
    private val savedStateHandle: SavedStateHandle
) : BaseMviViewModel<ProfileContract.State, ProfileContract.Event, ProfileContract.Effect>(initialState = ProfileContract.State()) {

    override fun handleEvent(event: ProfileContract.Event) {
        when (event) {
            is ProfileContract.Event.UpdateFirstName -> setState { copy(firstName = event.value) }
            is ProfileContract.Event.UpdateLastName -> setState { copy(lastName = event.value) }
            is ProfileContract.Event.UpdateEmail -> setState { copy(email = event.value) }
            is ProfileContract.Event.UpdateCity -> setState { copy(city = event.value) }
            is ProfileContract.Event.UpdateChurch -> setState { copy(church = event.value) }
        }
    }
}
