package com.spasinnya.mentoring.presentation.screens.authflow.login

import com.spasinnya.mentoring.domain.usecase.LoginUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : BaseMviViewModel<LoginContract.State, LoginContract.Effect, LoginContract.Event>() {

    override fun createInitialState(): LoginContract.State = LoginContract.State()

    override fun handleEvent(event: LoginContract.Effect) {
        when (event) {

            else -> Unit
        }
    }
}