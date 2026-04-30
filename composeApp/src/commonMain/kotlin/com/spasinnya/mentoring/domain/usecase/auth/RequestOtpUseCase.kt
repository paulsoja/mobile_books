package com.spasinnya.mentoring.domain.usecase.auth

import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.Email
import com.spasinnya.mentoring.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class RequestOtpCodeUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(email: Email.Valid): Flow<DomainResult<Unit>> =
        authRepository.requestOtp(email)
}
