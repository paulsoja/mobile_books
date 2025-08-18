package com.spasinnya.mentoring.domain.repository

import com.spasinnya.mentoring.data.model.CredentialsApiRequest
import com.spasinnya.mentoring.domain.model.AuthToken
import com.spasinnya.mentoring.domain.model.OtpCode
import kotlinx.coroutines.flow.Flow

typealias LoginRepository = suspend (CredentialsApiRequest) -> AuthToken?
typealias RegisterRepository = (CredentialsApiRequest) -> Flow<String>
typealias OtpRepository = suspend (CredentialsApiRequest, OtpCode) -> Boolean