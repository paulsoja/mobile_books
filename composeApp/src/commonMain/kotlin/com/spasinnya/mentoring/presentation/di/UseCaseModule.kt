package com.spasinnya.mentoring.presentation.di

import com.spasinnya.mentoring.domain.usecase.auth.ConfirmOtpCodeUseCase
import com.spasinnya.mentoring.domain.usecase.auth.LoginUseCase
import com.spasinnya.mentoring.domain.usecase.auth.RegisterUseCase

interface UseCaseModule {
    val loginUseCase: LoginUseCase
    val registerUseCase: RegisterUseCase
    val otpUseCase: ConfirmOtpCodeUseCase
}