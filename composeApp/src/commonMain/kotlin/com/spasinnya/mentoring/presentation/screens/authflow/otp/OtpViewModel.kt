package com.spasinnya.mentoring.presentation.screens.authflow.otp

import com.spasinnya.mentoring.presentation.base.BaseMviViewModel

class OtpViewModel() : BaseMviViewModel<OtpContract.State, OtpContract.Event, OtpContract.Effect>() {

    override fun createInitialState(): OtpContract.State = OtpContract.State()

    override fun handleEvent(event: OtpContract.Event) {
        when (event) {

            else -> Unit
        }
    }
}