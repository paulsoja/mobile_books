package com.spasinnya.mentoring.data.model

import kotlinx.serialization.Serializable

@Serializable
data class WeekResponse(
    val id: Int,
    val bookId: Int,
    val title: String,
    val number: Int
)
