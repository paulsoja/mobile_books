package com.spasinnya.mentoring.domain.repository

import com.spasinnya.mentoring.domain.model.BookMeta
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.HomeworkAnswer
import com.spasinnya.mentoring.domain.model.HomeworkProgress
import com.spasinnya.mentoring.domain.model.ParsedHomeworkWeek
import com.spasinnya.mentoring.domain.model.ParsedWeek
import com.spasinnya.mentoring.domain.model.PurchaseStatus
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    fun getBooks(): Flow<DomainResult<List<BookMeta>>>
    fun purchaseBook(bookId: String): Flow<DomainResult<PurchaseStatus>>
    fun getWeeks(bookId: String): Flow<DomainResult<BookMeta>>
    fun getHomeworkProgress(bookId: String): Flow<DomainResult<List<HomeworkProgress>>>
    fun observeBook(bookId: String, weekNumber: Int): Flow<DomainResult<ParsedWeek>>
    fun observeHomework(bookId: String, weekNumber: Int): Flow<DomainResult<ParsedHomeworkWeek>>

    fun getHomeworkAnswers(
        bookId: String,
        weekNumber: Int,
        lessonNumber: Int,
    ): Flow<DomainResult<List<HomeworkAnswer>>>

    fun saveHomeworkAnswers(
        bookId: String,
        weekNumber: Int,
        lessonNumber: Int,
        answers: List<HomeworkAnswer>,
    ): Flow<DomainResult<List<Int>>>

    fun getCompletedLessons(
        bookId: String,
        weekNumber: Int,
    ): Flow<DomainResult<List<Int>>>
}
