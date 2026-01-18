package com.spasinnya.mentoring.presentation.screens.homeflow.settings

import com.spasinnya.mentoring.domain.enums.Language

sealed interface SettingsSheetAction
sealed interface SettingsSheetDismissAction : SettingsSheetAction

data object Close : SettingsSheetDismissAction
data class LanguageChosen(val lang: Language) : SettingsSheetDismissAction
data object ProfileClick : SettingsSheetDismissAction
data object PromoCodesClick : SettingsSheetDismissAction
data object AuthorsClick : SettingsSheetDismissAction
data object SpasinnyaBooksClick : SettingsSheetDismissAction
data object SpasinnyaChurchClick : SettingsSheetDismissAction

data object LogoutClick : SettingsSheetAction