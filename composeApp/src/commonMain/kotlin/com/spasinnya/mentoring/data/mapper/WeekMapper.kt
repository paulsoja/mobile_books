package com.spasinnya.mentoring.data.mapper

import com.spasinnya.mentoring.data.model.WeekResponse
import com.spasinnya.mentoring.domain.model.Week

fun WeekResponse.toDomain(): Week = Week(
    id = id,
    bookId = bookId,
    number = number,
    title = title
)