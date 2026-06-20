package com.spasinnya.mentoring.domain.usecase.auth

import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class LogoutUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Flow<DomainResult<Unit>> =
        authRepository.logout()
}
