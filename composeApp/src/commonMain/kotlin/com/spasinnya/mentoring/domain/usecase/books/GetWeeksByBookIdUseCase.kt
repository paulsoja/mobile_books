package com.spasinnya.mentoring.domain.usecase.books

import com.spasinnya.mentoring.domain.model.Week
import com.spasinnya.mentoring.domain.repository.WeeksRepository
import com.spasinnya.mentoring.domain.rules.DomainResult
import kotlinx.coroutines.flow.Flow

typealias GetWeeksUseCase = suspend (bookId: Int) -> Flow<DomainResult<List<Week>>>

fun weeksUseCase(repository: WeeksRepository): GetWeeksUseCase = { bookId ->
    repository.invoke(bookId)
}