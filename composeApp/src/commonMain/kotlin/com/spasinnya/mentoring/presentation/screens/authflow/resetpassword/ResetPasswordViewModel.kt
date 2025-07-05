package com.spasinnya.mentoring.presentation.screens.authflow.resetpassword

import com.spasinnya.mentoring.presentation.base.BaseMviViewModel

class ResetPasswordViewModel() : BaseMviViewModel<ResetPasswordContract.State, ResetPasswordContract.Event, ResetPasswordContract.Effect>() {

    override fun createInitialState(): ResetPasswordContract.State = ResetPasswordContract.State()

    override fun handleEvent(event: ResetPasswordContract.Event) {
        when (event) {

            else -> Unit
        }
    }
}