package com.spasinnya.mentoring.presentation.screens.authflow.newpassword

import com.spasinnya.mentoring.presentation.base.BaseMviViewModel

class NewPasswordViewModel() : BaseMviViewModel<NewPasswordContract.State, NewPasswordContract.Event, NewPasswordContract.Effect>() {

    override fun createInitialState(): NewPasswordContract.State = NewPasswordContract.State()

    override fun handleEvent(event: NewPasswordContract.Event) {
        when (event) {

            else -> Unit
        }
    }
}