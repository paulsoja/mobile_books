package com.spasinnya.mentoring.domain.usecase.auth

import com.spasinnya.mentoring.data.mapper.toData
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.ResetPasswordCredentials
import com.spasinnya.mentoring.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class ResetPasswordUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(credentials: ResetPasswordCredentials): Flow<DomainResult<Unit>> =
        authRepository.resetPassword(credentials.toData())
}
