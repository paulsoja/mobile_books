package com.spasinnya.mentoring.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BookWeekResponse(
    val weekNumber: Int,
    val weekTitle: String,
    val lessons: List<BookLessonResponse>,
)
