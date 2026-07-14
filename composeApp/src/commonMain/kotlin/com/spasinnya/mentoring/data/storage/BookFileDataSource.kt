package com.spasinnya.mentoring.data.storage

import com.spasinnya.mentoring.data.model.BookMetaResponse
import com.spasinnya.mentoring.data.model.BooksIndexResponse
import com.spasinnya.mentoring.generated.resources.Res
import io.github.aakira.napier.Napier
import kotlinx.serialization.json.Json
import okio.FileSystem

class BookFileDataSource(
    private val fileSystem: FileSystem,
) {
    private val json = Json {
        ignoreUnknownKeys = true
    }

    suspend fun readWeekMarkdown(
        bookId: String,
        weekNumber: Int,
    ): String {
        val path = "files/books/$bookId/week$weekNumber.md"
        Napier.d { "Reading $path" }
        return Res.readBytes(path).decodeToString()
    }

    suspend fun readMetaJson(
        bookId: String,
    ): BookMetaResponse {
        val path = "files/books/$bookId/meta.json"
        val content = Res.readBytes(path).decodeToString()
        return json.decodeFromString<BookMetaResponse>(content)
    }

    suspend fun readBooksIndex(): BooksIndexResponse {
        val path = "files/books/index.json"
        Napier.d { "Reading $path" }

        val content = Res.readBytes(path).decodeToString()
        return json.decodeFromString<BooksIndexResponse>(content)
    }

    suspend fun readBookIds(
        language: String,
    ): List<String> =
        readBooksIndex()
            .books
            .filter { it.language == language }
            .map { it.id }

    suspend fun readAllMetaJson(
        language: String,
    ): List<BookMetaResponse> =
        readBookIds(language)
            .map { bookId -> readMetaJson(bookId) }
            .sortedBy { it.bookNumber }
}