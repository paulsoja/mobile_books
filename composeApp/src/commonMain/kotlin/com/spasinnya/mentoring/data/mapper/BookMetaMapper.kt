package com.spasinnya.mentoring.data.mapper

import com.spasinnya.mentoring.data.model.BookMetaResponse
import com.spasinnya.mentoring.domain.model.BookMeta

fun BookMetaResponse.toDomain() = BookMeta(
    id = id,
    title = title,
    description = description,
    coverImage = coverImage,
    previewImage = previewImage,
    bookNumber = bookNumber,
    language = language,
    author = author,
    subtitle = subtitle,
    productId = productId,
    priceLabel = priceLabel,
    weeksCount = weeksCount,
    lessonsPerWeek = lessonsPerWeek,
    tableOfContents = tableOfContents.map { it.toDomain() }
)