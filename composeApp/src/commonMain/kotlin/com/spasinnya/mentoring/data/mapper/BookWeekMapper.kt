package com.spasinnya.mentoring.data.mapper

import com.spasinnya.mentoring.data.model.BookWeekResponse
import com.spasinnya.mentoring.domain.model.BookWeek

fun BookWeekResponse.toDomain(): BookWeek {
    return BookWeek(
        weekNumber = weekNumber,
        weekTitle = weekTitle,
        lessons = lessons.map { it.toDomain() }
    )
}
