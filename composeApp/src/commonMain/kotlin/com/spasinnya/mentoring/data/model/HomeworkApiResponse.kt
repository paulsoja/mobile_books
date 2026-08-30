package com.spasinnya.mentoring.data.model

import kotlinx.serialization.Serializable

@Serializable
data class HomeworkApiResponse(
    val bookId: String,
    val completedLessons: Int,
)
