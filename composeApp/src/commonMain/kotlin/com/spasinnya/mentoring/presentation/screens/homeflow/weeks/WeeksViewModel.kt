package com.spasinnya.mentoring.presentation.screens.homeflow.weeks

import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.usecase.books.GetWeeksUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import com.spasinnya.mentoring.presentation.base.Validated
import com.spasinnya.mentoring.presentation.base.withLoading
import com.spasinnya.mentoring.presentation.model.UiErrorType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WeeksViewModel(
    private val bookId: String,
    private val weeksUseCase: GetWeeksUseCase
) : BaseMviViewModel<WeeksContract.State, WeeksContract.Event, WeeksContract.Effect>(initialState = WeeksContract.State()) {

    override fun handleEvent(event: WeeksContract.Event) {
        when (event) {
            WeeksContract.Event.Refresh -> loadWeeks(isRefresh = true)
            WeeksContract.Event.Retry -> {
                setState { copy(error = UiErrorType.None) }
                loadWeeks()
            }
        }
    }

    init {
        loadWeeks()
    }

    private fun loadWeeks(isRefresh: Boolean = false) = viewModelScope.launch(Dispatchers.IO) {
        weeksUseCase.invoke(bookId)
            .withLoading { loading ->
                setState { if (isRefresh) copy(isRefreshing = loading) else copy(isLoading = loading) }
            }
            .collectLatest { result ->
                when (result) {
                    is Validated.Invalid -> handleDomainErrors(
                        error = result.error,
                        reduce = { errorType ->
                            copy(
                                isLoading = false,
                                isRefreshing = false,
                                error = errorType
                            )
                        }
                    )
                    is Validated.Valid -> setState {
                        copy(bookMeta = result.value, error = UiErrorType.None)
                    }
                }
            }
    }
}
