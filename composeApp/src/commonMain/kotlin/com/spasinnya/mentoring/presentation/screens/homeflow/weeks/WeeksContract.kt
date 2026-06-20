package com.spasinnya.mentoring.presentation.screens.homeflow.weeks

import com.spasinnya.mentoring.domain.model.BookMeta

interface WeeksContract {
    data class State(
        val isLoading: Boolean = false,
        val bookMeta: BookMeta? = null,
    )

    sealed class Event {

    }

    sealed class Effect {

    }
}