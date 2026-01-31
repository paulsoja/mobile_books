package com.spasinnya.mentoring.presentation.screens.homeflow.home

import com.spasinnya.mentoring.domain.enums.Language
import com.spasinnya.mentoring.domain.model.ShortBook
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.DialogState
import com.spasinnya.mentoring.presentation.model.UiMessageType

interface HomeContract {
    data class State(
        val books: List<ShortBook> = emptyList(),
        val isLoading: Boolean = false,
        val isPurchaseLoading: Boolean = false,
        val showSettingsDialog: Boolean = false,
        val logoutDialog: DialogState<UiMessageType> = DialogState.Hidden,
    )

    sealed class Event {
        data object Logout : Event()
        data object OnProfileClick : Event()
        data class LoadedBooks(val books: List<ShortBook>) : Event()
        data class ShowLoading(val isLoading: Boolean) : Event()
        data class PurchaseLoading(val isLoading: Boolean) : Event()
        data class PurchaseBook(val bookId: Int) : Event()
        data class SetPurchasedBook(val bookId: Int) : Event()
        data class ToggleSettingsDialog(val show: Boolean) : Event()
        data class ToggleLogoutDialog(val show: Boolean) : Event()
        data class OnLanguageChosen(val language: Language) : Event()
    }

    sealed class Effect {
        data object NavigateToLogin : Effect()
        data object NavigateToProfile : Effect()
        data class ShowSnackbar(val message: String) : Effect()
    }
}