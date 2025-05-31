package com.spasinnya.mentoring.presentation.di

import com.spasinnya.mentoring.data.repository.loginRepo
import com.spasinnya.mentoring.data.repository.otpRepo
import com.spasinnya.mentoring.data.repository.registerRepo
import com.spasinnya.mentoring.domain.usecase.createLoginUseCase
import com.spasinnya.mentoring.domain.usecase.createOtpUseCase
import com.spasinnya.mentoring.domain.usecase.createRegisterUseCase

object UseCaseProvider {
    val loginUseCase by lazy { createLoginUseCase(repository = loginRepo) }
    val registerUseCase by lazy { createRegisterUseCase(repository = registerRepo) }
    val otpUseCase by lazy { createOtpUseCase(repository = otpRepo) }
}