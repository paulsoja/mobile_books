package com.spasinnya.mentoring.presentation.di

import com.spasinnya.mentoring.domain.usecase.auth.AuthStepsUseCase
import com.spasinnya.mentoring.domain.usecase.auth.ChangeCongratsShownStatusUseCase
import com.spasinnya.mentoring.domain.usecase.auth.ConfirmOtpCodeUseCase
import com.spasinnya.mentoring.domain.usecase.auth.LoginUseCase
import com.spasinnya.mentoring.domain.usecase.auth.LogoutUseCase
import com.spasinnya.mentoring.domain.usecase.auth.RegisterUseCase
import com.spasinnya.mentoring.domain.usecase.auth.RequestOtpCodeUseCase
import com.spasinnya.mentoring.domain.usecase.auth.changeCongratsShownStatusUseCase
import com.spasinnya.mentoring.domain.usecase.auth.checkAuthStepsUseCase
import com.spasinnya.mentoring.domain.usecase.auth.confirmOtpCodeUseCase
import com.spasinnya.mentoring.domain.usecase.auth.loginUseCase
import com.spasinnya.mentoring.domain.usecase.auth.logoutUseCase
import com.spasinnya.mentoring.domain.usecase.auth.registerUseCase
import com.spasinnya.mentoring.domain.usecase.auth.requestOtpCodeUseCase
import com.spasinnya.mentoring.domain.usecase.books.GetBooksUseCase
import com.spasinnya.mentoring.domain.usecase.books.GetLessonsUseCase
import com.spasinnya.mentoring.domain.usecase.books.GetWeeksUseCase
import com.spasinnya.mentoring.domain.usecase.books.PurchaseBookUseCase
import com.spasinnya.mentoring.domain.usecase.books.getBooksUseCase
import com.spasinnya.mentoring.domain.usecase.books.lessonsUseCase
import com.spasinnya.mentoring.domain.usecase.books.purchaseBookUseCase
import com.spasinnya.mentoring.domain.usecase.books.weeksUseCase
import com.spasinnya.mentoring.domain.usecase.settings.GetAppLocaleUseCase
import com.spasinnya.mentoring.domain.usecase.settings.SetAppLocaleUseCase
import com.spasinnya.mentoring.domain.usecase.settings.appLocaleUseCase
import com.spasinnya.mentoring.domain.usecase.settings.changeAppLocaleUseCase

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
        override val requestOtpUseCase: RequestOtpCodeUseCase by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            requestOtpCodeUseCase(repos.requestOtpRepository)
        }
        override val logoutUseCase: LogoutUseCase by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            logoutUseCase(repos.logoutRepository)
        }
        override val authStepsUseCase: AuthStepsUseCase by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            checkAuthStepsUseCase(repos.tokenRepository, repos.congratsShownRepository)
        }
        override val changeCongratsShownStatusUseCase: ChangeCongratsShownStatusUseCase by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            changeCongratsShownStatusUseCase(repos.changeCongratsShownRepository)
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
        override val getAppLocaleUseCase: GetAppLocaleUseCase by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            appLocaleUseCase(repos.localeRepository)
        }
        override val setAppLocaleUseCase: SetAppLocaleUseCase by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            changeAppLocaleUseCase(repos.setLocaleRepository)
        }
    }