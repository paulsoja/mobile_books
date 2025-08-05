package com.spasinnya.mentoring.domain.usecase

import com.spasinnya.mentoring.domain.model.AuthToken
import com.spasinnya.mentoring.domain.model.Credentials
import com.spasinnya.mentoring.domain.model.OtpCode
import kotlinx.coroutines.flow.Flow

typealias LoginUseCase = suspend (Credentials) -> AuthToken
typealias RegisterUseCase = suspend (Credentials) -> Flow<String>
typealias OtpUseCase = suspend (Credentials, OtpCode) -> Unit