package com.spasinnya.mentoring.domain.usecase.auth

import com.spasinnya.mentoring.data.mapper.toData
import com.spasinnya.mentoring.domain.model.Credentials
import com.spasinnya.mentoring.domain.model.OtpCode
import com.spasinnya.mentoring.domain.repository.OtpRepository

typealias OtpUseCase = suspend (Credentials, OtpCode) -> Unit

fun otpUseCase(repository: OtpRepository): OtpUseCase = { credentials, code ->
    if (!repository(credentials.toData(), code)) throw IllegalArgumentException("OTP validation failed")
}