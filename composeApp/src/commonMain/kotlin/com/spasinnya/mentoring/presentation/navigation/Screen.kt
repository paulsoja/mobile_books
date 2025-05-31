package com.spasinnya.mentoring.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    sealed class AuthFlow : Screen() {
        @Serializable
        data object LoginScreen : AuthFlow()
        @Serializable
        data object RegisterScreen : AuthFlow()
        @Serializable
        data object OtpScreen : AuthFlow()
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
        data object WeeksScreen : HomeFlow()
        @Serializable
        data object LessonsScreen : HomeFlow()
    }
}