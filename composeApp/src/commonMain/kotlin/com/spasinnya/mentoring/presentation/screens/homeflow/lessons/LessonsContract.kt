package com.spasinnya.mentoring.presentation.screens.homeflow.lessons

import com.spasinnya.mentoring.domain.model.Lesson

interface LessonsContract {
    data class State(
        val lessons: List<Lesson> = emptyList(),
        val isLoading: Boolean = false,
    )

    sealed class Event {

    }

    sealed class Effect {

    }
}