package com.spasinnya.mentoring.presentation.screens.homeflow.weeks

import com.spasinnya.mentoring.domain.model.Week

interface WeeksContract {
    data class State(
        val isLoading: Boolean = false,
        val weeks: List<Week> = emptyList(),
    )

    sealed class Event {

    }

    sealed class Effect {

    }
}