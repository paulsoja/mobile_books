package com.spasinnya.mentoring.presentation.screens.homeflow.weeks

import com.spasinnya.mentoring.domain.model.BookMeta
import com.spasinnya.mentoring.presentation.model.UiErrorType

interface WeeksContract {
    data class State(
        val isLoading: Boolean = false,
        val isRefreshing: Boolean = false,
        val error: UiErrorType = UiErrorType.None,
        val bookMeta: BookMeta? = null,
    )

    sealed class Event {
        data object Refresh : Event()
        data object Retry : Event()
    }

    sealed class Effect {

    }
}
