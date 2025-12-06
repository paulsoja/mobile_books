package com.spasinnya.mentoring.presentation.screens.homeflow.lessons

import com.spasinnya.mentoring.domain.model.Lesson

interface LessonsContract {
    data class State(
        val lessons: List<Lesson> = emptyList(),
        val isLoading: Boolean = false,
    )

    sealed class Event {
        data class LoadedLessons(val lessons: List<Lesson>): Event()
        data class ShowLoading(val show: Boolean): Event()
    }

    sealed class Effect {

    }

    sealed class ErrorType {
        data object NoConnection : ErrorType()
    }
}