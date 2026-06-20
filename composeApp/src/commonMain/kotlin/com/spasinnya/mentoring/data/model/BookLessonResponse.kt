package com.spasinnya.mentoring.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BookLessonResponse(
    val lessonNumber: Int,
    val title: String,
)
