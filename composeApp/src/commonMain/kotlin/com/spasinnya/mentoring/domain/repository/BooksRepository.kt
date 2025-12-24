package com.spasinnya.mentoring.domain.repository

import com.spasinnya.mentoring.domain.model.Lesson
import com.spasinnya.mentoring.domain.model.PurchaseStatus
import com.spasinnya.mentoring.domain.model.ShortBook
import com.spasinnya.mentoring.domain.model.Week
import com.spasinnya.mentoring.domain.rules.DomainResult
import kotlinx.coroutines.flow.Flow

typealias BooksRepository = suspend () -> Flow<DomainResult<List<ShortBook>>>
typealias PurchaseBookRepository = suspend (bookId: Int) -> Flow<DomainResult<PurchaseStatus>>
typealias WeeksRepository = suspend (bookId: Int) -> Flow<DomainResult<List<Week>>>
typealias LessonsRepository = suspend (weekId: Int) -> Flow<DomainResult<List<Lesson>>>