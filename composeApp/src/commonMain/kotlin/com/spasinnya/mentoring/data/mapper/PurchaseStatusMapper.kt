package com.spasinnya.mentoring.data.mapper

import com.spasinnya.mentoring.data.model.PurchaseStatusApiResponse
import com.spasinnya.mentoring.domain.model.PurchaseStatus

fun PurchaseStatusApiResponse.toDomain() = PurchaseStatus(
    bookId = bookId,
    purchased = purchased
)