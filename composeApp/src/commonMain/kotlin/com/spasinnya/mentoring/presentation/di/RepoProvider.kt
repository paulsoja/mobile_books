package com.spasinnya.mentoring.presentation.di

import com.spasinnya.mentoring.data.repository.booksRepository
import com.spasinnya.mentoring.data.repository.changeShownCongratsStatus
import com.spasinnya.mentoring.data.repository.lessonsRepository
import com.spasinnya.mentoring.data.repository.loginRepository
import com.spasinnya.mentoring.data.repository.logoutRepo
import com.spasinnya.mentoring.data.repository.otpRepo
import com.spasinnya.mentoring.data.repository.purchaseBookRepository
import com.spasinnya.mentoring.data.repository.registerRepo
import com.spasinnya.mentoring.data.repository.requestOtpCode
import com.spasinnya.mentoring.data.repository.shownCongrats
import com.spasinnya.mentoring.data.repository.tokens
import com.spasinnya.mentoring.data.repository.weeksRepository
import com.spasinnya.mentoring.domain.repository.BooksRepository
import com.spasinnya.mentoring.domain.repository.ChangeCongratsShownRepository
import com.spasinnya.mentoring.domain.repository.CongratsShownRepository
import com.spasinnya.mentoring.domain.repository.LessonsRepository
import com.spasinnya.mentoring.domain.repository.LoginRepository
import com.spasinnya.mentoring.domain.repository.LogoutRepository
import com.spasinnya.mentoring.domain.repository.OtpRepository
import com.spasinnya.mentoring.domain.repository.PurchaseBookRepository
import com.spasinnya.mentoring.domain.repository.RegisterRepository
import com.spasinnya.mentoring.domain.repository.RequestOtpRepository
import com.spasinnya.mentoring.domain.repository.TokenRepository
import com.spasinnya.mentoring.domain.repository.WeeksRepository
import io.ktor.client.HttpClient

fun provideRepoModule(http: HttpClient, dataSourceModule: DataSourceModule): RepoModule =
    object : RepoModule {
        override val loginRepository: LoginRepository by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            loginRepository(http, dataSourceModule.tokenStore)
        }
        override val registerRepository: RegisterRepository by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            registerRepo(http)
        }
        override val otpRepository: OtpRepository by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            otpRepo(http, dataSourceModule.tokenStore)
        }
        override val requestOtpRepository: RequestOtpRepository by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            requestOtpCode(http)
        }
        override val logoutRepository: LogoutRepository by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            logoutRepo(http, dataSourceModule.tokenStore, dataSourceModule.appStore)
        }
        override val congratsShownRepository: CongratsShownRepository by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            shownCongrats(dataSourceModule.appStore)
        }
        override val changeCongratsShownRepository: ChangeCongratsShownRepository by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            changeShownCongratsStatus(dataSourceModule.appStore)
        }
        override val tokenRepository: TokenRepository by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            tokens(dataSourceModule.tokenStore)
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