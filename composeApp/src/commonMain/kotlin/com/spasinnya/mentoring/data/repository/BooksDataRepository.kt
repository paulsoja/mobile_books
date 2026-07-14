package com.spasinnya.mentoring.data.repository

import com.spasinnya.mentoring.data.mapper.toDataError
import com.spasinnya.mentoring.data.mapper.toDomain
import com.spasinnya.mentoring.data.mapper.toDomainError
import com.spasinnya.mentoring.data.model.PurchaseStatusApiResponse
import com.spasinnya.mentoring.data.net.postFlow
import com.spasinnya.mentoring.data.parser.MentorshipMarkdownParser
import com.spasinnya.mentoring.data.storage.BookFileDataSource
import com.spasinnya.mentoring.data.storage.datastore.LocaleStore
import com.spasinnya.mentoring.domain.model.*
import com.spasinnya.mentoring.domain.repository.BooksRepository
import com.spasinnya.mentoring.presentation.base.Validated
import com.spasinnya.mentoring.presentation.base.map
import com.spasinnya.mentoring.presentation.base.mapErrors
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class BooksDataRepository(
    private val bookFileDataSource: BookFileDataSource,
    private val http: HttpClient,
    private val parser: MentorshipMarkdownParser,
    private val localeStore: LocaleStore,
) : BooksRepository {

    override fun getBooks(): Flow<DomainResult<List<BookMeta>>> = flow {
        val currentLanguage = localeStore.read()
        val result = try {
            val meta = bookFileDataSource.readAllMetaJson(currentLanguage ?: "uk")
            Validated.Valid(meta.map { it.toDomain() })
        } catch (t: Throwable) {
            Validated.Invalid(t.toDataError())
        }.mapErrors { it.toDomainError() }
        emit(result)
    }

    override fun purchaseBook(bookId: String): Flow<DomainResult<PurchaseStatus>> =
        http.postFlow<Unit, PurchaseStatusApiResponse>(
            path = "books/$bookId/purchase",
        ).map { result ->
            result
                .map(PurchaseStatusApiResponse::toDomain)
                .mapErrors { it.toDomainError() }
        }

    override fun getWeeks(bookId: String): Flow<DomainResult<BookMeta>> = flow {
        val result = try {
            val meta = bookFileDataSource.readMetaJson(bookId)
            Validated.Valid(meta.toDomain())
        } catch (t: Throwable) {
            Validated.Invalid(t.toDataError())
        }.mapErrors { it.toDomainError() }
        emit(result)
    }

    override fun observeBook(bookId: String, weekNumber: Int): Flow<DomainResult<ParsedWeek>> = flow {
        val result = try {
            val markdown = bookFileDataSource.readWeekMarkdown(
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
            Napier.d { "observeBook: ${t}" }
            Validated.Invalid(DomainError.Unknown)
        }
        emit(result)
    }
}
