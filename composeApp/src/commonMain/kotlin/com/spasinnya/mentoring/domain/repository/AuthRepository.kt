package com.spasinnya.mentoring.domain.repository

import com.spasinnya.mentoring.data.model.CredentialsApiRequest
import com.spasinnya.mentoring.data.model.OtpCredentialsApiRequest
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.Email
import com.spasinnya.mentoring.domain.model.Token
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(credentials: CredentialsApiRequest): Flow<DomainResult<Token>>
    fun register(credentials: CredentialsApiRequest): Flow<DomainResult<String>>
    fun otp(credentials: OtpCredentialsApiRequest): Flow<DomainResult<Token>>
    fun requestOtp(email: Email.Valid): Flow<DomainResult<Unit>>
    fun logout(): Flow<DomainResult<Unit>>
}
