package com.spasinnya.mentoring.domain.repository

import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.Homework
import kotlinx.coroutines.flow.Flow

interface HomeworkRepository {
    fun getHomework(): Flow<DomainResult<List<Homework>>>
}
