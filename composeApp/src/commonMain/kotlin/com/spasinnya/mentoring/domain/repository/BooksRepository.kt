package com.spasinnya.mentoring.domain.repository

import com.spasinnya.mentoring.domain.model.Lesson
import com.spasinnya.mentoring.domain.model.PurchaseStatus
import com.spasinnya.mentoring.domain.model.ShortBook
import com.spasinnya.mentoring.domain.model.Week
import kotlinx.coroutines.flow.Flow

typealias BooksRepository = suspend () -> Flow<List<ShortBook>>
typealias PurchaseBookRepository = suspend (bookId: Int) -> Flow<PurchaseStatus>
typealias WeeksRepository = suspend (bookId: Int) -> Flow<List<Week>>
typealias LessonsRepository = suspend (weekId: Int) -> Flow<List<Lesson>>