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
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.map
import kotlin.collections.map

fun booksRepository(
    http: HttpClient,
): BooksRepository = {
    http.getFlow< List<ShortBookApiResponse>>(
        path = "books",
    )
        .map { it.map(ShortBookApiResponse::toDomain) }
}

fun purchaseBookRepository(
    http: HttpClient,
): PurchaseBookRepository = { bookId ->
    http.postFlow<Unit, PurchaseStatusApiResponse>(
        path = "books/$bookId/purchase",
    )
        .map(PurchaseStatusApiResponse::toDomain)
}

fun weeksRepository(
    http: HttpClient,
): WeeksRepository = { bookId ->
    http.getFlow<List<WeekResponse>>(
        path = "weeks/$bookId",
    )
        .map { it.map(WeekResponse::toDomain) }
}

fun lessonsRepository(
    http: HttpClient,
): LessonsRepository = { weekId ->
    http.getFlow<List<LessonResponse>>(
        path = "lessons/$weekId",
    )
        .map { it.map(LessonResponse::toDomain) }
}
