package com.spasinnya.mentoring.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PurchaseStatusApiResponse(
    val bookId: Int,
    val purchased: Boolean
)
