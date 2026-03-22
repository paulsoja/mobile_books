package com.spasinnya.mentoring.domain.usecase.auth

import com.spasinnya.mentoring.data.mapper.toData
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.OtpCredentials
import com.spasinnya.mentoring.domain.model.Token
import com.spasinnya.mentoring.domain.repository.OtpRepository
import kotlinx.coroutines.flow.Flow

typealias ConfirmOtpCodeUseCase = suspend (OtpCredentials) -> Flow<DomainResult<Token>>

fun confirmOtpCodeUseCase(repository: OtpRepository): ConfirmOtpCodeUseCase = { otpCreds ->
    repository(otpCreds.toData())
}