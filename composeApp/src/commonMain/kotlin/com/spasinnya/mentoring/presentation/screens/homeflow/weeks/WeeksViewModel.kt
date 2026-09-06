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

    private enum class LoadMode { Initial, Manual, Silent }

    private var isInitialResume = true

    override fun handleEvent(event: WeeksContract.Event) {
        when (event) {
            WeeksContract.Event.Refresh -> loadWeeks(LoadMode.Manual)
            WeeksContract.Event.ScreenResumed ->
                if (isInitialResume) isInitialResume = false else loadWeeks(LoadMode.Silent)
            WeeksContract.Event.Retry -> {
                setState { copy(error = UiErrorType.None) }
                loadWeeks(LoadMode.Initial)
            }
        }
    }

    init {
        loadWeeks(LoadMode.Initial)
    }

    private fun loadWeeks(mode: LoadMode) = viewModelScope.launch(Dispatchers.IO) {
        weeksUseCase.invoke(bookId)
            .withLoading { loading ->
                when (mode) {
                    LoadMode.Initial -> setState { copy(isLoading = loading) }
                    LoadMode.Manual -> setState { copy(isRefreshing = loading) }
                    LoadMode.Silent -> Unit
                }
            }
            .collectLatest { result ->
                when (result) {
                    is Validated.Invalid -> if (mode != LoadMode.Silent) {
                        handleDomainErrors(
                            error = result.error,
                            reduce = { errorType ->
                                copy(
                                    isLoading = false,
                                    isRefreshing = false,
                                    error = errorType
                                )
                            }
                        )
                    }
                    is Validated.Valid -> setState {
                        copy(bookMeta = result.value, error = UiErrorType.None)
                    }
                }
            }
    }
}
