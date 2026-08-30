package com.spasinnya.mentoring.data.repository

import com.spasinnya.mentoring.data.mapper.toDomain
import com.spasinnya.mentoring.data.mapper.toDomainError
import com.spasinnya.mentoring.data.model.HomeworkApiResponse
import com.spasinnya.mentoring.data.net.getFlow
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.Homework
import com.spasinnya.mentoring.domain.repository.HomeworkRepository
import com.spasinnya.mentoring.presentation.base.map
import com.spasinnya.mentoring.presentation.base.mapErrors
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HomeworkDataRepository(
    private val http: HttpClient,
) : HomeworkRepository {

    override fun getHomework(): Flow<DomainResult<List<Homework>>> =
        http.getFlow<List<HomeworkApiResponse>>(
            path = "homework",
        ).map { result ->
            result
                .map { list -> list.map(HomeworkApiResponse::toDomain) }
                .mapErrors { it.toDomainError() }
        }
}
