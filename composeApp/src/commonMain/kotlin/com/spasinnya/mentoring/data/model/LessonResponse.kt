package com.spasinnya.mentoring.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LessonResponse(
    val id: Long,
    val weekId: Long,
    val number: Int,
    val title: String,
    val quote: String? = null,
    val content: List<LessonContentResponse>
)
