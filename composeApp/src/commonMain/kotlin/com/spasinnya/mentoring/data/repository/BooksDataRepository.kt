package com.spasinnya.mentoring.data.repository

import com.spasinnya.mentoring.data.mapper.toDomain
import com.spasinnya.mentoring.data.mapper.toDomainError
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
import com.spasinnya.mentoring.presentation.base.map
import com.spasinnya.mentoring.presentation.base.mapErrors
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.map
import kotlin.collections.map

fun booksRepository(
    http: HttpClient,
): BooksRepository = {
    http.getFlow<List<ShortBookApiResponse>>(
        path = "books",
    )
        .map { result ->
            result
                .map { it.map(ShortBookApiResponse::toDomain) }
                .mapErrors { it.toDomainError() }
        }
}

fun purchaseBookRepository(
    http: HttpClient,
): PurchaseBookRepository = { bookId ->
    http.postFlow<Unit, PurchaseStatusApiResponse>(
        path = "books/$bookId/purchase",
    )
        .map { result ->
            result
                .map(PurchaseStatusApiResponse::toDomain)
                .mapErrors { it.toDomainError() }
        }
}

fun weeksRepository(
    http: HttpClient,
): WeeksRepository = { bookId ->
    http.getFlow<List<WeekResponse>>(
        path = "weeks/$bookId",
    )
        .map { result ->
            result
                .map { it.map(WeekResponse::toDomain) }
                .mapErrors { it.toDomainError() }
        }
}

fun lessonsRepository(
    http: HttpClient,
): LessonsRepository = { weekId ->
    http.getFlow<List<LessonResponse>>(
        path = "lessons/$weekId",
    )
        .map { result ->
            result
                .map { it.map(LessonResponse::toDomain) }
                .mapErrors { it.toDomainError() }
        }
}
