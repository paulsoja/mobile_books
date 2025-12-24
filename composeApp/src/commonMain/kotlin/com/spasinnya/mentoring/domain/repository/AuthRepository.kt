package com.spasinnya.mentoring.domain.repository

import com.spasinnya.mentoring.data.model.CredentialsApiRequest
import com.spasinnya.mentoring.data.model.OtpCredentialsApiRequest
import com.spasinnya.mentoring.domain.model.Token
import com.spasinnya.mentoring.domain.rules.DomainResult
import kotlinx.coroutines.flow.Flow

typealias LoginRepository = suspend (CredentialsApiRequest) -> Flow<DomainResult<Token>>
typealias RegisterRepository = (CredentialsApiRequest) -> Flow<DomainResult<String>>
typealias OtpRepository = suspend (OtpCredentialsApiRequest) -> Flow<DomainResult<Token>>
typealias LogoutRepository = suspend () -> Flow<DomainResult<Unit>>