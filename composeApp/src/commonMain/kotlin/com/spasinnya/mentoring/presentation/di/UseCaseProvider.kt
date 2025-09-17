package com.spasinnya.mentoring.presentation.di

import com.spasinnya.mentoring.data.repository.loginRepository
import com.spasinnya.mentoring.data.repository.otpRepo
import com.spasinnya.mentoring.data.repository.registerRepo
import com.spasinnya.mentoring.data.storage.datastore.TokenStore
import com.spasinnya.mentoring.domain.repository.LoginRepository
import com.spasinnya.mentoring.domain.repository.OtpRepository
import com.spasinnya.mentoring.domain.repository.RegisterRepository
import com.spasinnya.mentoring.domain.usecase.auth.ConfirmOtpCodeUseCase
import com.spasinnya.mentoring.domain.usecase.auth.LoginUseCase
import com.spasinnya.mentoring.domain.usecase.auth.RegisterUseCase
import com.spasinnya.mentoring.domain.usecase.auth.loginUseCase
import com.spasinnya.mentoring.domain.usecase.auth.confirmOtpCodeUseCase
import com.spasinnya.mentoring.domain.usecase.auth.registerUseCase
import io.ktor.client.HttpClient

fun provideRepoModule(http: HttpClient, tokenStore: TokenStore): RepoModule =
    object : RepoModule {
        override val loginRepository: LoginRepository by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            loginRepository(http, tokenStore)
        }
        override val registerRepository: RegisterRepository by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            registerRepo(http)
        }
        override val otpRepository: OtpRepository by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            otpRepo(http, tokenStore)
        }
    }

fun provideUseCaseModule(repos: RepoModule): UseCaseModule =
    object : UseCaseModule {
        override val loginUseCase: LoginUseCase by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            loginUseCase(repos.loginRepository)
        }
        override val registerUseCase: RegisterUseCase by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            registerUseCase(repos.registerRepository)
        }
        override val otpUseCase: ConfirmOtpCodeUseCase by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            confirmOtpCodeUseCase(repos.otpRepository)
        }
    }