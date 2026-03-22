package com.spasinnya.mentoring.presentation.screens.homeflow.weeks

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.usecase.books.GetWeeksUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import com.spasinnya.mentoring.presentation.base.Validated
import com.spasinnya.mentoring.presentation.base.withLoading
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WeeksViewModel(
    private val bookId: Int,
    private val weeksUseCase: GetWeeksUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseMviViewModel<WeeksContract.State, WeeksContract.Event, WeeksContract.Effect>(initialState = WeeksContract.State()) {

    override fun handleEvent(event: WeeksContract.Event) {
        when (event) {
            else -> {}
        }
    }

    init {
        loadWeeks()
    }

    private fun loadWeeks() = viewModelScope.launch(Dispatchers.IO) {
        weeksUseCase.invoke(bookId)
            .withLoading { loading -> setState { copy(isLoading = loading) } }
            .collectLatest { result ->
                when (result) {
                    is Validated.Invalid -> handleDomainErrors(
                        error = result.error,
                        reduce = { errorType ->
                            copy(
                                isLoading = false,
                            )
                        }
                    )
                    is Validated.Valid -> setState { copy(weeks = result.value) }
                }
            }
    }
}