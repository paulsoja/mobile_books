package com.spasinnya.mentoring.presentation.auth

import androidx.lifecycle.ViewModel
import com.spasinnya.mentoring.domain.usecase.LoginUseCase
import com.spasinnya.mentoring.domain.usecase.OtpUseCase
import com.spasinnya.mentoring.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val otpUseCase: OtpUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state: StateFlow<AuthState> = _state

    /*fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            runCatching {
                loginUseCase(Credentials(email, password))
            }.onSuccess { token ->
                _state.value = AuthState.LoginSuccess(token)
            }.onFailure { e ->
                _state.value = AuthState.Error(e.message.orEmpty())
            }
        }
    }*/

    /*fun register(email: String, password: String) {
        viewModelScope.launch {
            _state.value = AuthState.Loading
            runCatching {
                registerUseCase(Credentials(email, password))
            }.onSuccess { token ->
                _state.value = AuthState.RegisterStep(email, password)
            }.onFailure { e ->
                _state.value = AuthState.Error(e.message.orEmpty())
            }
        }
    }*/

    /*fun verifyOtp(email: String, password: String, code: String) {
        viewModelScope.launch {
            _state.value = AuthState.Loading

            runCatching {
                otpUseCase(Credentials(email, password), OtpCode(code))
            }.onSuccess { token ->
                _state.value = AuthState.OtpSuccess("Registration complete")
            }.onFailure { e ->
                _state.value = AuthState.Error(e.message.orEmpty())
            }
        }
    }*/
}