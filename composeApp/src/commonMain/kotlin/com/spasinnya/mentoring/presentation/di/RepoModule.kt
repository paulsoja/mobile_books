package com.spasinnya.mentoring.presentation.di

import com.spasinnya.mentoring.domain.repository.BooksRepository
import com.spasinnya.mentoring.domain.repository.ChangeCongratsShownRepository
import com.spasinnya.mentoring.domain.repository.CongratsShownRepository
import com.spasinnya.mentoring.domain.repository.LessonsRepository
import com.spasinnya.mentoring.domain.repository.LocaleRepository
import com.spasinnya.mentoring.domain.repository.LoginRepository
import com.spasinnya.mentoring.domain.repository.LogoutRepository
import com.spasinnya.mentoring.domain.repository.OtpRepository
import com.spasinnya.mentoring.domain.repository.PurchaseBookRepository
import com.spasinnya.mentoring.domain.repository.RegisterRepository
import com.spasinnya.mentoring.domain.repository.RequestOtpRepository
import com.spasinnya.mentoring.domain.repository.SetLocaleRepository
import com.spasinnya.mentoring.domain.repository.TokenRepository
import com.spasinnya.mentoring.domain.repository.WeeksRepository

interface RepoModule {
    val loginRepository: LoginRepository
    val registerRepository: RegisterRepository
    val otpRepository: OtpRepository
    val requestOtpRepository: RequestOtpRepository
    val logoutRepository: LogoutRepository
    val congratsShownRepository: CongratsShownRepository
    val changeCongratsShownRepository: ChangeCongratsShownRepository
    val tokenRepository: TokenRepository
    val localeRepository: LocaleRepository
    val setLocaleRepository: SetLocaleRepository
    val booksRepository: BooksRepository
    val purchaseBookRepository: PurchaseBookRepository
    val weeksRepository: WeeksRepository
    val lessonsRepository: LessonsRepository
}