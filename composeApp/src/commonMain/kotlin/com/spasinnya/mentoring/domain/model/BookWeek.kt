package com.spasinnya.mentoring.domain.model

data class BookWeek(
    val weekNumber: Int,
    val weekTitle: String,
    val lessons: List<BookLesson>,
    val completed: Int = 0,
) {
    val progress: Float
        get() = if (lessons.isEmpty()) 0f else (completed.toFloat() / lessons.size).coerceIn(0f, 1f)

    val isCompleted: Boolean
        get() = lessons.isNotEmpty() && completed >= lessons.size
}
