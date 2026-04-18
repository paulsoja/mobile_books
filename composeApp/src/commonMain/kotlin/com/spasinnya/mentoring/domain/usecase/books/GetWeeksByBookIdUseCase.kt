package com.spasinnya.mentoring.domain.usecase.books

import com.spasinnya.mentoring.domain.model.BookMeta
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.repository.WeeksRepository
import kotlinx.coroutines.flow.Flow

typealias GetWeeksUseCase = suspend (bookId: String) -> Flow<DomainResult<BookMeta>>

fun weeksUseCase(repository: WeeksRepository): GetWeeksUseCase = { bookId ->
    repository.invoke(bookId)
}