package com.spasinnya.mentoring.presentation.navigation

import kotlinx.serialization.Serializable

sealed class ScreenContainer {
    @Serializable
    data object AuthFlow : ScreenContainer()
    @Serializable
    data object SettingsFlow : ScreenContainer()
    @Serializable
    data object HomeFlow : ScreenContainer()
    @Serializable
    data object PaymentFlow : ScreenContainer()
    @Serializable
    data object PromoCodesFlow : ScreenContainer()
    @Serializable
    data object LessonsFlow : ScreenContainer()
}