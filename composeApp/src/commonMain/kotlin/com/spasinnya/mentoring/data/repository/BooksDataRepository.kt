package com.spasinnya.mentoring.data.repository

import com.spasinnya.mentoring.data.mapper.toDataError
import com.spasinnya.mentoring.data.mapper.toDomain
import com.spasinnya.mentoring.data.mapper.toDomainError
import com.spasinnya.mentoring.data.model.PurchaseStatusApiResponse
import com.spasinnya.mentoring.data.net.postFlow
import com.spasinnya.mentoring.data.parser.MentorshipMarkdownParser
import com.spasinnya.mentoring.data.storage.BookFileDataSource
import com.spasinnya.mentoring.domain.model.DomainError
import com.spasinnya.mentoring.domain.repository.BookReaderRepository
import com.spasinnya.mentoring.domain.repository.BooksRepository
import com.spasinnya.mentoring.domain.repository.PurchaseBookRepository
import com.spasinnya.mentoring.domain.repository.WeeksRepository
import com.spasinnya.mentoring.presentation.base.Validated
import com.spasinnya.mentoring.presentation.base.map
import com.spasinnya.mentoring.presentation.base.mapErrors
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

fun booksRepository(
    bookFileDataSource: BookFileDataSource,
): BooksRepository = {
    flow {
        val test = try {
            val result = bookFileDataSource.readAllMetaJson("en")
            Validated.Valid(result.map { it.toDomain() })
        } catch (t: Throwable) {
            Validated.Invalid(t.toDataError())
        }.mapErrors { it.toDomainError() }
        emit(test)
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
    bookFileDataSource: BookFileDataSource,
): WeeksRepository = { bookId ->
    flow {
        val test = try {
            val result = bookFileDataSource.readMetaJson(bookId)
            Validated.Valid(result.toDomain())
        } catch (t: Throwable) {
            Validated.Invalid(t.toDataError())
        }.mapErrors { it.toDomainError() }
        emit(test)
    }
}

fun bookReaderRepository(
    resourceDataSource: BookFileDataSource,
    parser: MentorshipMarkdownParser,
): BookReaderRepository = { bookId, weekNumber ->
    flow {
        val result = try {
            val markdown = resourceDataSource.readWeekMarkdown(
                bookId = bookId,
                weekNumber = weekNumber,
            )

            if (markdown.isBlank()) {
                Validated.Invalid(DomainError.EmptyContent)
            } else {
                val parsedWeek = parser.parseWeek(markdown)
                Validated.Valid(parsedWeek)
            }
        } catch (t: Throwable) {
            Napier.d { "bookReaderRepository: ${t}" }
            Validated.Invalid(DomainError.Unknown)
        }

        emit(result)
    }
}
