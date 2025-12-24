package com.spasinnya.mentoring.data.repository

import com.spasinnya.mentoring.data.mapper.toDomain
import com.spasinnya.mentoring.data.model.LessonResponse
import com.spasinnya.mentoring.data.model.PurchaseStatusApiResponse
import com.spasinnya.mentoring.data.model.ShortBookApiResponse
import com.spasinnya.mentoring.data.model.WeekResponse
import com.spasinnya.mentoring.data.net.getFlow
import com.spasinnya.mentoring.data.net.postFlow
import com.spasinnya.mentoring.domain.repository.BooksRepository
import com.spasinnya.mentoring.domain.repository.LessonsRepository
import com.spasinnya.mentoring.domain.repository.PurchaseBookRepository
import com.spasinnya.mentoring.domain.repository.WeeksRepository
import com.spasinnya.mentoring.presentation.base.mapValid
import io.ktor.client.HttpClient
import kotlin.collections.map

fun booksRepository(
    http: HttpClient,
): BooksRepository = {
    http.getFlow<List<ShortBookApiResponse>>(
        path = "books",
    )
        .mapValid { it.map(ShortBookApiResponse::toDomain) }
}

fun purchaseBookRepository(
    http: HttpClient,
): PurchaseBookRepository = { bookId ->
    http.postFlow<Unit, PurchaseStatusApiResponse>(
        path = "books/$bookId/purchase",
    )
        .mapValid(PurchaseStatusApiResponse::toDomain)
}

fun weeksRepository(
    http: HttpClient,
): WeeksRepository = { bookId ->
    http.getFlow<List<WeekResponse>>(
        path = "weeks/$bookId",
    )
        .mapValid { it.map(WeekResponse::toDomain) }
}

fun lessonsRepository(
    http: HttpClient,
): LessonsRepository = { weekId ->
    http.getFlow<List<LessonResponse>>(
        path = "lessons/$weekId",
    )
        .mapValid { it.map(LessonResponse::toDomain) }
}
