package com.spasinnya.mentoring.presentation.screens.homeflow.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.enums.Language
import com.spasinnya.mentoring.domain.model.ShortBook
import com.spasinnya.mentoring.domain.usecase.auth.LogoutUseCase
import com.spasinnya.mentoring.domain.usecase.books.GetBooksUseCase
import com.spasinnya.mentoring.domain.usecase.books.PurchaseBookUseCase
import com.spasinnya.mentoring.domain.usecase.settings.SetAppLocaleUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import com.spasinnya.mentoring.presentation.base.Validated
import com.spasinnya.mentoring.presentation.base.withLoading
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.DialogState
import com.spasinnya.mentoring.presentation.di.resetAppGraph
import com.spasinnya.mentoring.presentation.model.UiMessageType
import com.spasinnya.mentoring.presentation.screens.homeflow.home.HomeContract.Effect.ShowSnackbar
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class HomeViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val getBooksUseCase: GetBooksUseCase,
    private val purchaseBookUseCase: PurchaseBookUseCase,
    private val setAppLocaleUseCase: SetAppLocaleUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseMviViewModel<HomeContract.State, HomeContract.Event, HomeContract.Effect>(initialState = HomeContract.State()) {

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.Logout -> logout()
            is HomeContract.Event.ToggleSettingsDialog -> setState { copy(showSettingsDialog = event.show) }
            is HomeContract.Event.ToggleLogoutDialog -> toggleLogoutDialog(event.show)
            is HomeContract.Event.OnLanguageChosen -> setNewLanguage(event.language)
            is HomeContract.Event.LoadedBooks -> setState { copy(books = event.books) }
            is HomeContract.Event.ShowLoading -> setState { copy(isLoading = event.isLoading) }
            is HomeContract.Event.PurchaseBook -> purchaseBook(event.bookId)
            is HomeContract.Event.SetPurchasedBook -> {
                setState { copy(books = books.setPurchasedBook(event.bookId)) }
                sendEffect { ShowSnackbar("congrats") }
            }

            is HomeContract.Event.PurchaseLoading -> setState { copy(isPurchaseLoading = event.isLoading) }
            is HomeContract.Event.OnProfileClick -> sendEffect { HomeContract.Effect.NavigateToProfile }
        }
    }

    init {
        loadBooks()
    }

    private fun loadBooks() = viewModelScope.launch(Dispatchers.IO) {
        getBooksUseCase.invoke()
            .withLoading { loading -> setState { copy(isLoading = loading) } }
            .collectLatest { result ->
                when (result) {
                    is Validated.Invalid -> handleDomainErrors(
                        errors = result.errors,
                        reduce = { errorType ->
                            copy(
                                isLoading = false,
                                // TODO: add error fields and handle it here
                            )
                        }
                    )
                    is Validated.Valid -> dispatchEvent(HomeContract.Event.LoadedBooks(result.value))
                }

            }
    }

    private fun purchaseBook(bookId: Int) = viewModelScope.launch(Dispatchers.IO) {
        purchaseBookUseCase.invoke(bookId)
            .withLoading { loading -> setState { copy(isPurchaseLoading = loading) } }
            .collectLatest { result ->
                when (result) {
                    is Validated.Invalid -> handleDomainErrors(
                        errors = result.errors,
                        reduce = { errorType ->
                            copy(
                                isLoading = false,
                                // TODO: add error fiels and handle it here
                            )
                        }
                    )
                    is Validated.Valid -> dispatchEvent(HomeContract.Event.SetPurchasedBook(bookId))
                }
            }
    }

    private fun setNewLanguage(language: Language) = viewModelScope.launch(Dispatchers.IO) {
        Napier.d("setNewLanguage: 1=$language")
        setAppLocaleUseCase(language).collect()
    }

    private fun List<ShortBook>.setPurchasedBook(bookId: Int): List<ShortBook> {
        return this.map { if (it.id == bookId) it.copy(isPurchased = true) else it }
    }

    private fun logout() = viewModelScope.launch {
        logoutUseCase.invoke()
            .onCompletion {
                resetAppGraph()
            }
            .collect()
    }

    private fun toggleLogoutDialog(show: Boolean) {
        setState {
            copy(logoutDialog = if (show) DialogState.Shown(UiMessageType.Logout) else DialogState.Hidden)
        }
    }
}