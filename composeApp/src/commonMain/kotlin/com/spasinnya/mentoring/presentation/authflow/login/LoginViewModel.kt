package com.spasinnya.mentoring.presentation.authflow.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.spasinnya.mentoring.data.repository.loginRepo
import com.spasinnya.mentoring.domain.usecase.LoginUseCase
import com.spasinnya.mentoring.domain.usecase.createLoginUseCase

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                LoginViewModel(
                    loginUseCase = createLoginUseCase(loginRepo)
                )
            }
        }
    }
}