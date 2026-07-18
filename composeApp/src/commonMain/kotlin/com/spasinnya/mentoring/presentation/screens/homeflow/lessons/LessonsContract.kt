package com.spasinnya.mentoring.presentation.screens.homeflow.lessons

import com.spasinnya.mentoring.domain.model.ParsedLesson

interface LessonsContract {
    data class State(
        val lessons: List<ParsedLesson> = emptyList(),
        val weekNumber: Int,
        val weekTitle: String = "",
        /** Lesson numbers that have practical-work (homework) content. */
        val lessonsWithHomework: Set<Int> = emptySet(),
        /** Lesson whose practical-work sheet is open, or null when none. */
        val practicalWorkLesson: Int? = null,
        val isLoading: Boolean = false,
    )

    sealed class Event {
        data class OpenPracticalWork(val lessonNumber: Int) : Event()
        data object ClosePracticalWork : Event()
    }

    sealed class Effect {

    }
}