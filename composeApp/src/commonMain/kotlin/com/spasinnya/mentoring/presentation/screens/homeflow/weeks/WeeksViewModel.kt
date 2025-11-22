package com.spasinnya.mentoring.presentation.screens.homeflow.weeks

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.usecase.books.GetWeeksUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class WeeksViewModel(
    private val bookId: Int,
    private val weeksUseCase: GetWeeksUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseMviViewModel<WeeksContract.State, WeeksContract.Event, WeeksContract.Effect>() {

    override fun createInitialState(): WeeksContract.State = WeeksContract.State()

    override fun handleEvent(event: WeeksContract.Event) {
        when (event) {
            is WeeksContract.Event.LoadedWeeks -> setState { copy(weeks = event.weeks) }
            is WeeksContract.Event.ShowLoading -> setState { copy(isLoading = event.show) }
        }
    }

    init {
        loadWeeks()
    }

    private fun loadWeeks() = viewModelScope.launch(Dispatchers.IO) {
        weeksUseCase.invoke(bookId)
            .catch {
                Napier.d("loadWeeks: catch=$it")
            }
            .onStart { dispatchEvent(WeeksContract.Event.ShowLoading(true)) }
            .onCompletion { dispatchEvent(WeeksContract.Event.ShowLoading(false)) }
            .collectLatest { weeks ->
                dispatchEvent(WeeksContract.Event.LoadedWeeks(weeks))
            }
    }
}