package com.spasinnya.mentoring.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BookIndexItemResponse(
    val id: String,
    val language: String,
)
