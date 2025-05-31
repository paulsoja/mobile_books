package com.spasinnya.mentoring.domain.usecase

import com.spasinnya.mentoring.domain.models.AuthToken
import com.spasinnya.mentoring.domain.models.EmailPassword
import com.spasinnya.mentoring.domain.models.OtpCode

typealias LoginUseCase = suspend (EmailPassword) -> AuthToken
typealias RegisterUseCase = suspend (EmailPassword) -> Unit
typealias OtpUseCase = suspend (EmailPassword, OtpCode) -> Unit