package com.spasinnya.mentoring.presentation.screens.homeflow.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.model.ShortBook
import com.spasinnya.mentoring.domain.usecase.auth.LogoutUseCase
import com.spasinnya.mentoring.domain.usecase.books.GetBooksUseCase
import com.spasinnya.mentoring.domain.usecase.books.PurchaseBookUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import com.spasinnya.mentoring.presentation.di.resetAppGraph
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class HomeViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val getBooksUseCase: GetBooksUseCase,
    private val purchaseBookUseCase: PurchaseBookUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseMviViewModel<HomeContract.State, HomeContract.Event, HomeContract.Effect>() {

    override fun createInitialState(): HomeContract.State = HomeContract.State()

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.Logout -> sendEffect { HomeContract.Effect.NavigateToLogin }
            is HomeContract.Event.ToggleSettingsDialog -> setState { copy(showSettingsDialog = event.show) }
            is HomeContract.Event.OnLanguageChosen -> setState { copy(selectedLanguage = event.language) }
            is HomeContract.Event.LoadedBooks -> setState { copy(books = event.books) }
            is HomeContract.Event.ShowLoading -> setState { copy(isLoading = event.isLoading) }
            is HomeContract.Event.PurchaseBook -> purchaseBook(event.bookId)
            is HomeContract.Event.SetPurchasedBook -> {
                setState { copy(books = books.setPurchasedBook(event.bookId)) }
                sendEffect { HomeContract.Effect.ShowSnackbar("congrats") }
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
            .catch {
                Napier.d("loadBooks: catch=$it")
            }
            .onStart { dispatchEvent(HomeContract.Event.ShowLoading(true)) }
            .onCompletion { dispatchEvent(HomeContract.Event.ShowLoading(false)) }
            .collectLatest { books ->
                dispatchEvent(HomeContract.Event.LoadedBooks(books))
            }
    }

    private fun purchaseBook(bookId: Int) = viewModelScope.launch(Dispatchers.IO) {
        purchaseBookUseCase.invoke(bookId)
            .catch {
                Napier.d("loadBooks: catch=$it")
            }
            .onStart { dispatchEvent(HomeContract.Event.PurchaseLoading(true)) }
            .onCompletion { dispatchEvent(HomeContract.Event.PurchaseLoading(false)) }
            .collectLatest {
                if (it.purchased) {
                    dispatchEvent(HomeContract.Event.SetPurchasedBook(bookId))
                } else {
                    // TODO cant purchase
                }
            }
    }

    private fun List<ShortBook>.setPurchasedBook(bookId: Int): List<ShortBook> {
        return this.map { if (it.id == bookId) it.copy(isPurchased = true) else it }
    }

    fun logout() = viewModelScope.launch {
        logoutUseCase.invoke()
            .catch { }
            .onCompletion {
                resetAppGraph()
                dispatchEvent(HomeContract.Event.Logout)
            }
            .collect()
    }
}