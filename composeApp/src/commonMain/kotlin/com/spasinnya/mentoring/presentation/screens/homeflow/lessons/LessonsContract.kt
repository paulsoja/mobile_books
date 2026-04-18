package com.spasinnya.mentoring.presentation.screens.homeflow.lessons

import com.spasinnya.mentoring.domain.model.ParsedLesson

interface LessonsContract {
    data class State(
        val lessons: List<ParsedLesson> = emptyList(),
        val isLoading: Boolean = false,
    )

    sealed class Event {

    }

    sealed class Effect {

    }
}