package com.spasinnya.mentoring.domain.usecase

import com.spasinnya.mentoring.domain.repository.LoginRepository
import com.spasinnya.mentoring.domain.repository.OtpRepository
import com.spasinnya.mentoring.domain.repository.RegisterRepository

fun createLoginUseCase(repository: LoginRepository): LoginUseCase = { credentials ->
    repository(credentials) ?: throw IllegalArgumentException("Invalid login credentials")
}

fun createRegisterUseCase(repository: RegisterRepository): RegisterUseCase = { credentials ->
    if (!repository(credentials)) throw IllegalArgumentException("Registration failed")
}

fun createOtpUseCase(repository: OtpRepository): OtpUseCase = { credentials, code ->
    if (!repository(credentials, code)) throw IllegalArgumentException("OTP validation failed")
}