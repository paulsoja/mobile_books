package com.spasinnya.mentoring.domain.usecase.auth

import com.spasinnya.mentoring.domain.repository.LogoutRepository
import kotlinx.coroutines.flow.Flow

typealias LogoutUseCase = suspend () -> Flow<Unit>

fun logoutUseCase(repository: LogoutRepository): LogoutUseCase = {
    repository()
}