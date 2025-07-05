package com.spasinnya.mentoring.domain.usecase

import com.spasinnya.mentoring.domain.models.AuthToken
import com.spasinnya.mentoring.domain.models.Credentials
import com.spasinnya.mentoring.domain.models.OtpCode

typealias LoginUseCase = suspend (Credentials) -> AuthToken
typealias RegisterUseCase = suspend (Credentials) -> Unit
typealias OtpUseCase = suspend (Credentials, OtpCode) -> Unit