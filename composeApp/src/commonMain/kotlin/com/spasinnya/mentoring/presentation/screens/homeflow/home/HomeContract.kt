package com.spasinnya.mentoring.presentation.screens.homeflow.home

import com.spasinnya.mentoring.domain.enums.Language
import com.spasinnya.mentoring.domain.model.BookMeta
import com.spasinnya.mentoring.presentation.base.UiState
import com.spasinnya.mentoring.presentation.base.isAbsent
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.DialogState
import com.spasinnya.mentoring.presentation.model.UiErrorType
import com.spasinnya.mentoring.presentation.model.UiMessageType

interface HomeContract {
    data class State(
        val books: UiState<List<BookMeta>> = UiState.Absent,
        val isLoading: Boolean = false,
        val error: UiErrorType = UiErrorType.None,
        val isPurchaseLoading: Boolean = false,
        val showSettingsDialog: Boolean = false,
        val logoutDialog: DialogState<UiMessageType> = DialogState.Hidden,
    ) {
        val hasError: Boolean get() = error != UiErrorType.None
        val showLoader: Boolean get() = !hasError && (isLoading || books.isAbsent)
    }

    sealed class Event {
        data object Logout : Event()
        data object OnProfileClick : Event()
        data class LoadedBooks(val books: List<BookMeta>) : Event()
        data class ShowLoading(val isLoading: Boolean) : Event()
        data class PurchaseLoading(val isLoading: Boolean) : Event()
        data class PurchaseBook(val bookId: String) : Event()
        data class SetPurchasedBook(val bookId: String) : Event()
        data class ToggleSettingsDialog(val show: Boolean) : Event()
        data class ToggleLogoutDialog(val show: Boolean) : Event()
        data class OnLanguageChosen(val language: Language) : Event()
        data object ScreenResumed : Event()
        data object OnErrorClick : Event()
    }

    sealed class Effect {
        data object NavigateToLogin : Effect()
        data object NavigateToProfile : Effect()
        data class ShowSnackbar(val message: String) : Effect()
    }
}
