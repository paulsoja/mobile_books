package com.spasinnya.mentoring.domain.model

data class BookWeek(
    val weekNumber: Int,
    val weekTitle: String,
    val lessons: List<BookLesson>,
    val completed: Int = 0,
)
