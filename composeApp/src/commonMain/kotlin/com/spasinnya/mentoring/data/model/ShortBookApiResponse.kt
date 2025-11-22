package com.spasinnya.mentoring.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ShortBookApiResponse(
    val id: Int,
    val number: String,
    val title: String,
    val subtitle: String,
    val isPurchased: Boolean
)
