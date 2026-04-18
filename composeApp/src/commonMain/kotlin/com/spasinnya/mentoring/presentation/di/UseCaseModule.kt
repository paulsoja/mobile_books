package com.spasinnya.mentoring.presentation.di

import com.spasinnya.mentoring.domain.usecase.auth.AuthStepsUseCase
import com.spasinnya.mentoring.domain.usecase.auth.ChangeCongratsShownStatusUseCase
import com.spasinnya.mentoring.domain.usecase.auth.ConfirmOtpCodeUseCase
import com.spasinnya.mentoring.domain.usecase.auth.LoginUseCase
import com.spasinnya.mentoring.domain.usecase.auth.LogoutUseCase
import com.spasinnya.mentoring.domain.usecase.auth.RegisterUseCase
import com.spasinnya.mentoring.domain.usecase.auth.RequestOtpCodeUseCase
import com.spasinnya.mentoring.domain.usecase.books.GetBooksUseCase
import com.spasinnya.mentoring.domain.usecase.books.GetWeeksUseCase
import com.spasinnya.mentoring.domain.usecase.books.ObserveBookUseCase
import com.spasinnya.mentoring.domain.usecase.books.PurchaseBookUseCase
import com.spasinnya.mentoring.domain.usecase.settings.GetAppLocaleUseCase
import com.spasinnya.mentoring.domain.usecase.settings.SetAppLocaleUseCase

interface UseCaseModule {
    val loginUseCase: LoginUseCase
    val registerUseCase: RegisterUseCase
    val otpUseCase: ConfirmOtpCodeUseCase
    val requestOtpUseCase: RequestOtpCodeUseCase
    val logoutUseCase: LogoutUseCase
    val authStepsUseCase: AuthStepsUseCase
    val changeCongratsShownStatusUseCase: ChangeCongratsShownStatusUseCase
    val booksUseCase: GetBooksUseCase
    val purchaseBookUseCase: PurchaseBookUseCase
    val weeksUseCase: GetWeeksUseCase
    val getAppLocaleUseCase: GetAppLocaleUseCase
    val setAppLocaleUseCase: SetAppLocaleUseCase
    val importBookFromMarkdownUseCase: ObserveBookUseCase
}