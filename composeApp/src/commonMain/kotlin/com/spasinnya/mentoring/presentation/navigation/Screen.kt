package com.spasinnya.mentoring.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    sealed class AuthFlow : Screen() {
        @Serializable
        data object LoginScreen : AuthFlow()
        @Serializable
        data object RegisterScreen : AuthFlow()
        // purpose is an OtpPurpose name: enum route arguments are unsupported by navigation on iOS
        @Serializable
        data class OtpScreen(val email: String, val purpose: String) : AuthFlow()
        @Serializable
        data object ResetPasswordScreen : AuthFlow()
        @Serializable
        data class NewPasswordScreen(val email: String, val code: String) : AuthFlow()
    }

    sealed class HomeFlow : Screen() {
        @Serializable
        data object HomeScreen : HomeFlow()
        @Serializable
        data class WeeksScreen(val bookId: String, val bookNumber: Int) : HomeFlow()
        @Serializable
        data class LessonsScreen(val bookId: String, val weekNumber: Int) : HomeFlow()
        @Serializable
        data object ProfileScreen : HomeFlow()
    }
}