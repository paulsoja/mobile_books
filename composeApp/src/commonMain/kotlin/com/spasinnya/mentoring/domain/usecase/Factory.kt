package com.spasinnya.mentoring.domain.usecase

import com.spasinnya.mentoring.data.mapper.toData
import com.spasinnya.mentoring.domain.repository.LoginRepository
import com.spasinnya.mentoring.domain.repository.OtpRepository
import com.spasinnya.mentoring.domain.repository.RegisterRepository

fun createLoginUseCase(repository: LoginRepository): LoginUseCase = { credentials ->
    repository(credentials.toData()) ?: throw IllegalArgumentException("Invalid login credentials")
}

fun createRegisterUseCase(repository: RegisterRepository): RegisterUseCase = { credentials ->
        repository(credentials.toData())
        //if (!repository(credentials.toData())) throw IllegalArgumentException("Registration failed")
}

fun createOtpUseCase(repository: OtpRepository): OtpUseCase = { credentials, code ->
    if (!repository(credentials.toData(), code)) throw IllegalArgumentException("OTP validation failed")
}