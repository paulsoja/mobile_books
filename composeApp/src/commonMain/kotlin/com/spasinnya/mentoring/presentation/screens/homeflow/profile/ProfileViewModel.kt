package com.spasinnya.mentoring.presentation.screens.homeflow.profile

import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.model.UserProfile
import com.spasinnya.mentoring.domain.usecase.profile.GetProfileUseCase
import com.spasinnya.mentoring.domain.usecase.profile.UpdateProfileUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import com.spasinnya.mentoring.presentation.base.Validated
import com.spasinnya.mentoring.presentation.base.withLoading
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
) : BaseMviViewModel<ProfileContract.State, ProfileContract.Event, ProfileContract.Effect>(initialState = ProfileContract.State()) {

    init {
        loadProfile()
    }

    override fun handleEvent(event: ProfileContract.Event) {
        when (event) {
            is ProfileContract.Event.UpdateFirstName -> setState { copy(firstName = event.value) }
            is ProfileContract.Event.UpdateLastName -> setState { copy(lastName = event.value) }
            ProfileContract.Event.SaveProfile -> saveProfile()
        }
    }

    private fun loadProfile() = viewModelScope.launch(Dispatchers.IO) {
        getProfileUseCase()
            .withLoading { loading -> setState { copy(isLoading = loading) } }
            .collectLatest { result ->
                when (result) {
                    is Validated.Valid -> applyProfile(result.value)
                    is Validated.Invalid -> handleDomainErrors(
                        error = result.error,
                        reduce = { copy(isLoading = false) }
                    )
                }
            }
    }

    private fun saveProfile() = viewModelScope.launch(Dispatchers.IO) {
        val current = state.value
        updateProfileUseCase(firstName = current.firstName, lastName = current.lastName)
            .withLoading { loading -> setState { copy(isLoading = loading) } }
            .collectLatest { result ->
                when (result) {
                    is Validated.Valid -> {
                        applyProfile(result.value)
                        sendEffect { ProfileContract.Effect.ProfileSaved }
                    }
                    is Validated.Invalid -> {
                        handleDomainErrors(
                            error = result.error,
                            reduce = { copy(isLoading = false) }
                        )
                        sendEffect { ProfileContract.Effect.SaveFailed }
                    }
                }
            }
    }

    private fun applyProfile(profile: UserProfile) = setState {
        copy(
            firstName = profile.firstName.orEmpty(),
            lastName = profile.lastName.orEmpty(),
            email = profile.email,
        )
    }
}
