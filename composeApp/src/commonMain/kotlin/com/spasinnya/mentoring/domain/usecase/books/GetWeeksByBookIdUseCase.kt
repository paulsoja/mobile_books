package com.spasinnya.mentoring.domain.usecase.books

import com.spasinnya.mentoring.domain.model.BookMeta
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.repository.BooksRepository
import com.spasinnya.mentoring.domain.rules.zip
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip

class GetWeeksUseCase(
    private val booksRepository: BooksRepository
) {
    operator fun invoke(bookId: String): Flow<DomainResult<BookMeta>> =
        booksRepository.getWeeks(bookId).zip(booksRepository.getHomeworkProgress(bookId)) { weeks, progress ->
            weeks.zip(progress) { w, p ->
                val progressMap = p.associateBy { it.week_number }
                w.copy(
                    tableOfContents = w.tableOfContents.map { week ->
                        week.copy(completed = progressMap[week.weekNumber]?.completed ?: 0)
                    }
                )
            }
        }
}
