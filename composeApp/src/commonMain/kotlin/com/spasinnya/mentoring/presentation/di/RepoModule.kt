package com.spasinnya.mentoring.presentation.di

import com.spasinnya.mentoring.domain.repository.LoginRepository
import com.spasinnya.mentoring.domain.repository.LogoutRepository
import com.spasinnya.mentoring.domain.repository.OtpRepository
import com.spasinnya.mentoring.domain.repository.RegisterRepository

interface RepoModule {
    val loginRepository: LoginRepository
    val registerRepository: RegisterRepository
    val otpRepository: OtpRepository
    val logoutRepository: LogoutRepository
}