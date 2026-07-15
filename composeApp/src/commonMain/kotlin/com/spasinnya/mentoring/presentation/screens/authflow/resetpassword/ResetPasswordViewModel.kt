package com.spasinnya.mentoring.presentation.screens.authflow.resetpassword

import com.spasinnya.mentoring.domain.model.Email
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import com.spasinnya.mentoring.presentation.base.Validated

class ResetPasswordViewModel() :
    BaseMviViewModel<ResetPasswordContract.State, ResetPasswordContract.Event, ResetPasswordContract.Effect>(
        initialState = ResetPasswordContract.State()
    ) {

    override fun handleEvent(event: ResetPasswordContract.Event) {
        when (event) {
            is ResetPasswordContract.Event.EmailChanged -> emailChanged(event.email)
            ResetPasswordContract.Event.ValidateEmail -> validateEmail()
        }
    }

    private fun emailChanged(email: Email) = setState { copy(email = email, emailError = Email.Error.NoError) }

    private fun validateEmail() {
        when (val validated = state.value.email.validate()) {
            is Validated.Valid -> sendEffect { ResetPasswordContract.Effect.NavigateToOtp(validated.value.value) }
            is Validated.Invalid -> setState { copy(emailError = validated.error) }
        }
    }
}