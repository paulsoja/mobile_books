package com.spasinnya.mentoring.domain.usecase.auth

import com.spasinnya.mentoring.data.mapper.toData
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.OtpCredentials
import com.spasinnya.mentoring.domain.model.Token
import com.spasinnya.mentoring.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class ConfirmOtpCodeUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(otpCredentials: OtpCredentials): Flow<DomainResult<Token>> =
        authRepository.otp(otpCredentials.toData())
}
