package com.spasinnya.mentoring.domain.model

data class ShortBook(
    val id: Int,
    val number: String,
    val title: String,
    val subtitle: String,
    val isPurchased: Boolean
)
