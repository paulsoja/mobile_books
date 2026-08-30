package com.spasinnya.mentoring.domain.model

data class BookMeta(
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
    val progress: Int = 0,
    val isPurchased: Boolean = true,
    val tableOfContents: List<BookWeek>,
)
