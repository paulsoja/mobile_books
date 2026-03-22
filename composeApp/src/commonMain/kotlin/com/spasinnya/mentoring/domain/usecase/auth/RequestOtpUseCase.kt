package com.spasinnya.mentoring.domain.usecase.auth

import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.Email
import com.spasinnya.mentoring.domain.repository.RequestOtpRepository
import kotlinx.coroutines.flow.Flow

typealias RequestOtpCodeUseCase = suspend (Email.Valid) -> Flow<DomainResult<Unit>>

fun requestOtpCodeUseCase(repository: RequestOtpRepository): RequestOtpCodeUseCase = { email ->
    repository(email)
}