package com.spasinnya.mentoring.domain.usecase.books

import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.ParsedWeek
import com.spasinnya.mentoring.domain.repository.BookReaderRepository
import kotlinx.coroutines.flow.Flow

typealias ObserveBookUseCase = suspend (bookId: String, weekNumber: Int) -> Flow<DomainResult<ParsedWeek>>

fun observeBookUseCase(repository: BookReaderRepository): ObserveBookUseCase =
    { bookId, weekNumber ->
        repository.invoke(bookId, weekNumber)
    }