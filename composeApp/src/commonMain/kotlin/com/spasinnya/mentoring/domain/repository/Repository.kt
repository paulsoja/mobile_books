package com.spasinnya.mentoring.domain.repository

import com.spasinnya.mentoring.data.model.CredentialsApiRequest
import com.spasinnya.mentoring.data.model.OtpCredentialsApiRequest
import com.spasinnya.mentoring.domain.model.Token
import kotlinx.coroutines.flow.Flow

typealias LoginRepository = suspend (CredentialsApiRequest) -> Flow<Token>
typealias RegisterRepository = (CredentialsApiRequest) -> Flow<String>
typealias OtpRepository = suspend (OtpCredentialsApiRequest) -> Flow<Token>