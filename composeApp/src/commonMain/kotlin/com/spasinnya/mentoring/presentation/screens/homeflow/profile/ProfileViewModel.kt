package com.spasinnya.mentoring.presentation.screens.homeflow.profile

import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.model.UserProfile
import com.spasinnya.mentoring.domain.usecase.profile.GetProfileUseCase
import com.spasinnya.mentoring.domain.usecase.profile.DeleteAccountUseCase
import com.spasinnya.mentoring.domain.usecase.profile.UpdateProfileUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import com.spasinnya.mentoring.presentation.base.Validated
import com.spasinnya.mentoring.presentation.base.withLoading
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.DialogState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
) : BaseMviViewModel<ProfileContract.State, ProfileContract.Event, ProfileContract.Effect>(initialState = ProfileContract.State()) {

    init {
        loadProfile()
    }

    override fun handleEvent(event: ProfileContract.Event) {
        if (state.value.isLoading) return
        when (event) {
            is ProfileContract.Event.UpdateFirstName -> setState { copy(firstName = event.value) }
            is ProfileContract.Event.UpdateLastName -> setState { copy(lastName = event.value) }
            ProfileContract.Event.SaveProfile -> saveProfile()
            ProfileContract.Event.ShowDeleteConfirmation -> setState { copy(showDeleteConfirmation = true) }
            ProfileContract.Event.DismissDeleteConfirmation -> setState { copy(showDeleteConfirmation = false) }
            ProfileContract.Event.DeleteAccount -> deleteAccount()
            ProfileContract.Event.DismissDialog -> setState { copy(dialog = DialogState.Hidden) }
        }
    }

    private fun deleteAccount() {
        if (!state.value.showDeleteConfirmation) return
        setState { copy(showDeleteConfirmation = false, isLoading = true) }
        viewModelScope.launch {
            deleteAccountUseCase()
                .withLoading { loading -> setState { copy(isLoading = loading) } }
                .collectLatest { result ->
                    when (result) {
                        is Validated.Valid -> sendEffect { ProfileContract.Effect.AccountDeleted }
                        is Validated.Invalid -> handleDomainErrors(
                            error = result.error,
                            reduce = { errorType -> copy(isLoading = false, dialog = DialogState.Shown(errorType)) }
                        )
                    }
                }
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
                        reduce = { errorType -> copy(isLoading = false, dialog = DialogState.Shown(errorType)) }
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
                            reduce = { errorType -> copy(isLoading = false, dialog = DialogState.Shown(errorType)) }
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
