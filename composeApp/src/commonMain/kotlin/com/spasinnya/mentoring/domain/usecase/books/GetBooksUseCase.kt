package com.spasinnya.mentoring.domain.usecase.books

import com.spasinnya.mentoring.domain.model.BookMeta
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.repository.BooksRepository
import com.spasinnya.mentoring.domain.repository.HomeworkRepository
import com.spasinnya.mentoring.domain.rules.zip
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged

class GetBooksUseCase(
    private val booksRepository: BooksRepository,
    private val homeworkRepository: HomeworkRepository
) {
    operator fun invoke(): Flow<DomainResult<List<BookMeta>>> {
        val homeworks = homeworkRepository.getHomework().distinctUntilChanged()
        val books = booksRepository.getBooks().distinctUntilChanged()

        return combine(homeworks, books) { homeworkResult, bookResult ->
            bookResult.zip(homeworkResult) { books, homework ->
                books.map { book ->
                    book.copy(
                        progress = homework.find { it.bookId == book.id }?.completedLessons ?: 0
                    )
                }
            }
        }
    }
}
