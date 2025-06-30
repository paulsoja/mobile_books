package com.spasinnya.mentoring.presentation.screens.authflow.login

import androidx.lifecycle.ViewModelProvider
import com.spasinnya.mentoring.data.repository.loginRepo
import com.spasinnya.mentoring.domain.usecase.LoginUseCase
import com.spasinnya.mentoring.domain.usecase.createLoginUseCase
import com.spasinnya.mentoring.presentation.base.BaseViewModel

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : BaseViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = factory {
            LoginViewModel(loginUseCase = createLoginUseCase(loginRepo))
        }
    }
}