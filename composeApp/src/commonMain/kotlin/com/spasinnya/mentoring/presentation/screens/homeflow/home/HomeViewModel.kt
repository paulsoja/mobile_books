package com.spasinnya.mentoring.presentation.screens.homeflow.home

import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.enums.Language
import com.spasinnya.mentoring.domain.usecase.auth.LogoutUseCase
import com.spasinnya.mentoring.domain.usecase.books.GetBooksUseCase
import com.spasinnya.mentoring.domain.usecase.books.PurchaseBookUseCase
import com.spasinnya.mentoring.domain.usecase.settings.SetAppLocaleUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import com.spasinnya.mentoring.presentation.base.UiState
import com.spasinnya.mentoring.presentation.base.Validated
import com.spasinnya.mentoring.presentation.base.withLoading
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.DialogState
import com.spasinnya.mentoring.presentation.model.UiErrorType
import com.spasinnya.mentoring.presentation.model.UiMessageType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.isActive

class HomeViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val getBooksUseCase: GetBooksUseCase,
    private val purchaseBookUseCase: PurchaseBookUseCase,
    private val setAppLocaleUseCase: SetAppLocaleUseCase,
) : BaseMviViewModel<HomeContract.State, HomeContract.Event, HomeContract.Effect>(initialState = HomeContract.State()) {

    private enum class LoadMode { Initial, Silent }

    private var isInitialResume = true

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.Logout -> logout()
            is HomeContract.Event.ToggleSettingsDialog -> setState { copy(showSettingsDialog = event.show) }
            is HomeContract.Event.ToggleLogoutDialog -> toggleLogoutDialog(event.show)
            is HomeContract.Event.OnLanguageChosen -> setNewLanguage(event.language)
            is HomeContract.Event.LoadedBooks -> {
                setState { copy(books = UiState.Present(event.books), isLoading = false, error = UiErrorType.None) }
            }
            is HomeContract.Event.ShowLoading -> setState { copy(isLoading = event.isLoading) }
            is HomeContract.Event.PurchaseBook -> purchaseBook(event.bookId)
            is HomeContract.Event.OnProfileClick -> sendEffect { HomeContract.Effect.NavigateToProfile }
            is HomeContract.Event.ScreenResumed -> loadBooks(LoadMode.Silent)
            is HomeContract.Event.OnErrorClick -> {
                setState { copy(error = UiErrorType.None) }
                loadBooks()
            }
        }
    }

    init {
        loadBooks()
    }

    private fun loadBooks(mode: LoadMode = LoadMode.Initial) {
        viewModelScope.launch(Dispatchers.IO) {
            getBooksUseCase.invoke()
                .distinctUntilChanged()
                .withLoading { loading ->
                    if (mode == LoadMode.Initial) setState { copy(isLoading = loading) }
                }
                .collectLatest { result ->
                    when (result) {
                        is Validated.Invalid -> if (mode != LoadMode.Silent) {
                            handleDomainErrors(
                                error = result.error,
                                reduce = { errorType ->
                                    copy(
                                        isLoading = false,
                                        error = errorType
                                    )
                                }
                            )
                        }
                        is Validated.Valid -> {
                            dispatchEvent(HomeContract.Event.LoadedBooks(result.value))
                        }
                    }
                }
        }
    }

    private fun purchaseBook(bookId: String) {
        if (state.value.isPurchaseLoading) return
        setState { copy(isPurchaseLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                purchaseBookUseCase(bookId).collectLatest { result ->
                    when (result) {
                        is Validated.Invalid -> sendEffect { HomeContract.Effect.PurchaseMessage(PurchaseMessageKind.Failed) }
                        is Validated.Valid -> when {
                            result.value.purchased -> {
                                sendEffect { HomeContract.Effect.PurchaseMessage(PurchaseMessageKind.Success) }
                            }
                            result.value.pending -> sendEffect { HomeContract.Effect.PurchaseMessage(PurchaseMessageKind.Pending) }
                        }
                    }
                }
            } finally {
                setState { copy(isPurchaseLoading = false) }
                if (kotlinx.coroutines.currentCoroutineContext().isActive) loadBooks(LoadMode.Silent)
            }
        }
    }

    private fun setNewLanguage(language: Language) = viewModelScope.launch(Dispatchers.IO) {
        setAppLocaleUseCase(language).collect()
    }

    private fun logout() = viewModelScope.launch {
        logoutUseCase.invoke()
            .withLoading { loading -> setState { copy(isLoading = loading) } }
            .collectLatest { sendEffect { HomeContract.Effect.NavigateToLogin } }
    }

    private fun toggleLogoutDialog(show: Boolean) {
        setState {
            copy(logoutDialog = if (show) DialogState.Shown(UiMessageType.Logout) else DialogState.Hidden)
        }
    }
}