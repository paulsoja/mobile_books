package com.spasinnya.mentoring.data.mapper

import com.spasinnya.mentoring.data.model.ShortBookApiResponse
import com.spasinnya.mentoring.domain.model.ShortBook

fun ShortBookApiResponse.toDomain() = ShortBook(
    id = id,
    number = number,
    title = title,
    subtitle = subtitle,
    isPurchased = isPurchased
)