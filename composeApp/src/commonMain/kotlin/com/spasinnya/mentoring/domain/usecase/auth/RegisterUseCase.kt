package com.spasinnya.mentoring.domain.usecase.auth

import com.spasinnya.mentoring.data.mapper.toData
import com.spasinnya.mentoring.domain.model.Credentials
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class RegisterUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(credentials: Credentials): Flow<DomainResult<String>> =
        authRepository.register(credentials.toData())
}
