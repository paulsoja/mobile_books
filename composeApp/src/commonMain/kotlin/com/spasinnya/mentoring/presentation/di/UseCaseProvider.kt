package com.spasinnya.mentoring.presentation.di

import com.spasinnya.mentoring.data.repository.loginRepo
import com.spasinnya.mentoring.data.repository.otpRepo
import com.spasinnya.mentoring.data.repository.registerRepo
import com.spasinnya.mentoring.domain.usecase.auth.loginUseCase
import com.spasinnya.mentoring.domain.usecase.auth.otpUseCase
import com.spasinnya.mentoring.domain.usecase.auth.registerUseCase

object UseCaseProvider {
    val loginUseCase by lazy { loginUseCase(repository = loginRepo) }
    val registerUseCase by lazy { registerUseCase(repository = registerRepo) }
    val otpUseCase by lazy { otpUseCase(repository = otpRepo) }
}