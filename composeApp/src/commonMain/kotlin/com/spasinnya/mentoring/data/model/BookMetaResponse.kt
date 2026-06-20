package com.spasinnya.mentoring.data.model

import kotlinx.serialization.Serializable

@Serializable
data class BookMetaResponse(
    val id: String,
    val bookNumber: Int,
    val language: String,
    val title: String,
    val subtitle: String,
    val author: String,
    val description: String,
    val coverImage: String,
    val previewImage: String,
    val productId: String,
    val priceLabel: String,
    val weeksCount: Int,
    val lessonsPerWeek: Int,
    val tableOfContents: List<BookWeekResponse>,
)
