package com.spasinnya.mentoring.data.repository

import com.spasinnya.mentoring.data.mapper.toApiRequest
import com.spasinnya.mentoring.data.mapper.toDataError
import com.spasinnya.mentoring.data.mapper.toDomain
import com.spasinnya.mentoring.data.mapper.toDomainError
import com.spasinnya.mentoring.data.model.HomeworkAnswersApiRequest
import com.spasinnya.mentoring.data.model.HomeworkAnswersApiResponse
import com.spasinnya.mentoring.data.model.PurchaseStatusApiResponse
import com.spasinnya.mentoring.data.net.getFlow
import com.spasinnya.mentoring.data.net.postFlow
import com.spasinnya.mentoring.data.net.putFlow
import com.spasinnya.mentoring.data.parser.HomeworkMarkdownParser
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
    private val homeworkParser: HomeworkMarkdownParser,
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

    override fun getHomeworkProgress(bookId: String): Flow<DomainResult<List<HomeworkProgress>>> =
        http.getFlow<List<HomeworkProgress>>(
            path = "homework/$bookId",
        ).map { result ->
            result.mapErrors { it.toDomainError() }
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

    override fun getHomeworkAnswers(
        bookId: String,
        weekNumber: Int,
        lessonNumber: Int,
    ): Flow<DomainResult<List<HomeworkAnswer>>> =
        http.getFlow<HomeworkAnswersApiResponse>(
            path = homeworkAnswersPath(bookId, weekNumber, lessonNumber),
        ).map { result ->
            result
                .map(HomeworkAnswersApiResponse::toDomain)
                .mapErrors { it.toDomainError() }
        }

    override fun saveHomeworkAnswers(
        bookId: String,
        weekNumber: Int,
        lessonNumber: Int,
        answers: List<HomeworkAnswer>,
    ): Flow<DomainResult<List<Int>>> =
        http.putFlow<HomeworkAnswersApiRequest, List<Int>>(
            path = homeworkAnswersPath(bookId, weekNumber, lessonNumber),
            body = answers.toApiRequest(),
        ).map { result ->
            result.mapErrors { it.toDomainError() }
        }

    override fun getCompletedLessons(
        bookId: String,
        weekNumber: Int,
    ): Flow<DomainResult<List<Int>>> =
        http.getFlow<List<Int>>(
            path = "homework/$bookId/weeks/$weekNumber/lessons",
        ).map { result ->
            result.mapErrors { it.toDomainError() }
        }

    private fun homeworkAnswersPath(bookId: String, weekNumber: Int, lessonNumber: Int): String =
        "homework/$bookId/weeks/$weekNumber/lessons/$lessonNumber"

    override fun observeHomework(bookId: String, weekNumber: Int): Flow<DomainResult<ParsedHomeworkWeek>> = flow {
        val result = try {
            val markdown = bookFileDataSource.readHomeworkWeekMarkdown(
                bookId = bookId,
                weekNumber = weekNumber,
            )

            if (markdown.isBlank()) {
                Validated.Invalid(DomainError.EmptyContent)
            } else {
                val parsedHomework = homeworkParser.parseWeek(markdown)
                Validated.Valid(parsedHomework)
            }
        } catch (t: Throwable) {
            Napier.d { "observeHomework: $t" }
            Validated.Invalid(DomainError.Unknown)
        }
        emit(result)
    }
}
