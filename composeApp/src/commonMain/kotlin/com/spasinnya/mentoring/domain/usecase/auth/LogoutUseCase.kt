package com.spasinnya.mentoring.domain.usecase.auth

import com.spasinnya.mentoring.domain.repository.LogoutRepository
import com.spasinnya.mentoring.domain.rules.DomainResult
import kotlinx.coroutines.flow.Flow

typealias LogoutUseCase = suspend () -> Flow<DomainResult<Unit>>

fun logoutUseCase(repository: LogoutRepository): LogoutUseCase = {
    repository()
}