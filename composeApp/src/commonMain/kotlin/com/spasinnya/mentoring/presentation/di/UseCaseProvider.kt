package com.spasinnya.mentoring.presentation.di

import com.spasinnya.mentoring.data.repository.booksRepository
import com.spasinnya.mentoring.data.repository.lessonsRepository
import com.spasinnya.mentoring.data.repository.loginRepository
import com.spasinnya.mentoring.data.repository.logoutRepo
import com.spasinnya.mentoring.data.repository.otpRepo
import com.spasinnya.mentoring.data.repository.purchaseBookRepository
import com.spasinnya.mentoring.data.repository.registerRepo
import com.spasinnya.mentoring.data.repository.weeksRepository
import com.spasinnya.mentoring.data.storage.datastore.TokenStore
import com.spasinnya.mentoring.domain.repository.BooksRepository
import com.spasinnya.mentoring.domain.repository.LessonsRepository
import com.spasinnya.mentoring.domain.repository.LoginRepository
import com.spasinnya.mentoring.domain.repository.LogoutRepository
import com.spasinnya.mentoring.domain.repository.OtpRepository
import com.spasinnya.mentoring.domain.repository.PurchaseBookRepository
import com.spasinnya.mentoring.domain.repository.RegisterRepository
import com.spasinnya.mentoring.domain.repository.WeeksRepository
import com.spasinnya.mentoring.domain.usecase.auth.ConfirmOtpCodeUseCase
import com.spasinnya.mentoring.domain.usecase.auth.LoginUseCase
import com.spasinnya.mentoring.domain.usecase.auth.LogoutUseCase
import com.spasinnya.mentoring.domain.usecase.auth.RegisterUseCase
import com.spasinnya.mentoring.domain.usecase.auth.loginUseCase
import com.spasinnya.mentoring.domain.usecase.auth.confirmOtpCodeUseCase
import com.spasinnya.mentoring.domain.usecase.auth.logoutUseCase
import com.spasinnya.mentoring.domain.usecase.auth.registerUseCase
import com.spasinnya.mentoring.domain.usecase.books.GetBooksUseCase
import com.spasinnya.mentoring.domain.usecase.books.GetLessonsUseCase
import com.spasinnya.mentoring.domain.usecase.books.GetWeeksUseCase
import com.spasinnya.mentoring.domain.usecase.books.PurchaseBookUseCase
import com.spasinnya.mentoring.domain.usecase.books.getBooksUseCase
import com.spasinnya.mentoring.domain.usecase.books.lessonsUseCase
import com.spasinnya.mentoring.domain.usecase.books.purchaseBookUseCase
import com.spasinnya.mentoring.domain.usecase.books.weeksUseCase
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
        override val logoutRepository: LogoutRepository by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            logoutRepo(http, tokenStore)
        }
        override val booksRepository: BooksRepository by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            booksRepository(http)
        }
        override val purchaseBookRepository: PurchaseBookRepository by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            purchaseBookRepository(http)
        }
        override val weeksRepository: WeeksRepository by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            weeksRepository(http)
        }
        override val lessonsRepository: LessonsRepository by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            lessonsRepository(http)
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
        override val logoutUseCase: LogoutUseCase by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            logoutUseCase(repos.logoutRepository)
        }
        override val booksUseCase: GetBooksUseCase by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            getBooksUseCase(repos.booksRepository)
        }
        override val purchaseBookUseCase: PurchaseBookUseCase by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            purchaseBookUseCase(repos.purchaseBookRepository)
        }
        override val weeksUseCase: GetWeeksUseCase by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            weeksUseCase(repos.weeksRepository)
        }
        override val lessonsUseCase: GetLessonsUseCase by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            lessonsUseCase(repos.lessonsRepository)
        }
    }