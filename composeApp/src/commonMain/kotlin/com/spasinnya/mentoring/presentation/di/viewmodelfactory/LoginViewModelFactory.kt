package com.spasinnya.mentoring.presentation.di.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.spasinnya.mentoring.presentation.screens.authflow.login.LoginViewModel
import com.spasinnya.mentoring.presentation.di.UseCaseProvider.loginUseCase
import kotlin.reflect.KClass

class LoginViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
        if (modelClass.isInstance(LoginViewModel::class)) {
            return LoginViewModel(loginUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}