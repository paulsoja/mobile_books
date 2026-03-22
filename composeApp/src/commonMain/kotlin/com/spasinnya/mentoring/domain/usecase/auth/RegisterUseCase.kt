package com.spasinnya.mentoring.domain.usecase.auth

import com.spasinnya.mentoring.data.mapper.toData
import com.spasinnya.mentoring.domain.model.Credentials
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow

typealias RegisterUseCase = suspend (Credentials) -> Flow<DomainResult<String>>

fun registerUseCase(repository: RegisterRepository): RegisterUseCase = { credentials ->
    repository(credentials.toData())
}