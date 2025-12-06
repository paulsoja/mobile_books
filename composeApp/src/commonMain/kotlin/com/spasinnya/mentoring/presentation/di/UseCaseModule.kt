package com.spasinnya.mentoring.presentation.di

import com.spasinnya.mentoring.domain.usecase.auth.ConfirmOtpCodeUseCase
import com.spasinnya.mentoring.domain.usecase.auth.LoginUseCase
import com.spasinnya.mentoring.domain.usecase.auth.LogoutUseCase
import com.spasinnya.mentoring.domain.usecase.auth.RegisterUseCase
import com.spasinnya.mentoring.domain.usecase.books.GetBooksUseCase
import com.spasinnya.mentoring.domain.usecase.books.GetLessonsUseCase
import com.spasinnya.mentoring.domain.usecase.books.GetWeeksUseCase
import com.spasinnya.mentoring.domain.usecase.books.PurchaseBookUseCase

interface UseCaseModule {
    val loginUseCase: LoginUseCase
    val registerUseCase: RegisterUseCase
    val otpUseCase: ConfirmOtpCodeUseCase
    val logoutUseCase: LogoutUseCase
    val booksUseCase: GetBooksUseCase
    val purchaseBookUseCase: PurchaseBookUseCase
    val weeksUseCase: GetWeeksUseCase
    val lessonsUseCase: GetLessonsUseCase
}