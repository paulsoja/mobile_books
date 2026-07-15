package com.spasinnya.mentoring.presentation.screens.homeflow.lessons

import com.spasinnya.mentoring.domain.model.HomeworkLesson

interface PracticalWorkContract {
    data class State(
        val lessons: List<HomeworkLesson> = emptyList(),
        val checkedOptions: Map<String, Boolean> = emptyMap(),
        val textAnswers: Map<String, String> = emptyMap(),
        val isLoading: Boolean = false,
        val isError: Boolean = false,
    )

    sealed class Event {
        data class ToggleOption(val optionId: String, val checked: Boolean) : Event()
        data class ChangeText(val key: String, val value: String) : Event()
    }

    sealed class Effect
}
