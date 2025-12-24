package com.spasinnya.mentoring.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    sealed class AuthFlow : Screen() {
        @Serializable
        data object LoginScreen : AuthFlow()
        @Serializable
        data object RegisterScreen : AuthFlow()
        @Serializable
        data class OtpScreen(val email: String) : AuthFlow()
        @Serializable
        data object ResetPasswordScreen : AuthFlow()
        @Serializable
        data object NewPasswordScreen : AuthFlow()
        @Serializable
        data object CongratScreen : AuthFlow()
    }

    sealed class HomeFlow : Screen() {
        @Serializable
        data object HomeScreen : HomeFlow()
        @Serializable
        data class WeeksScreen(val bookId: Int, val bookNumber: String) : HomeFlow()
        @Serializable
        data class LessonsScreen(val weekId: Int) : HomeFlow()
        @Serializable
        data object ProfileScreen : HomeFlow()
    }
}