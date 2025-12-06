package com.spasinnya.mentoring.data.model

import kotlinx.serialization.Serializable

@Serializable
data class LessonContentResponse(
    val type: String, // "text" or "image"
    val data: String
)
