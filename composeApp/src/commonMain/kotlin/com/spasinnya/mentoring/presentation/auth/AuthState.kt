package com.spasinnya.mentoring.presentation.auth

import com.spasinnya.mentoring.domain.models.AuthToken

sealed class AuthState {
    data object Idle : AuthState()
    data object Loading : AuthState()
    data class LoginSuccess(val token: AuthToken) : AuthState()
    data class RegisterStep(val email: String, val password: String) : AuthState()
    data class OtpSuccess(val message: String) : AuthState()
    data class Error(val message: String) : AuthState()
}