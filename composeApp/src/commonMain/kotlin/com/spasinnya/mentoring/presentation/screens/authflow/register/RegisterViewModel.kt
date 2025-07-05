package com.spasinnya.mentoring.presentation.screens.authflow.register

import com.spasinnya.mentoring.presentation.base.BaseMviViewModel

class RegisterViewModel() : BaseMviViewModel<RegisterContract.State, RegisterContract.Event, RegisterContract.Effect>() {

    override fun createInitialState(): RegisterContract.State = RegisterContract.State()

    override fun handleEvent(event: RegisterContract.Event) {
        when (event) {

            else -> Unit
        }
    }

}