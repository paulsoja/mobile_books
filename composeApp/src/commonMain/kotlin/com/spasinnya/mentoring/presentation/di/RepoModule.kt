package com.spasinnya.mentoring.presentation.di

import com.spasinnya.mentoring.domain.repository.BooksRepository
import com.spasinnya.mentoring.domain.repository.LoginRepository
import com.spasinnya.mentoring.domain.repository.LogoutRepository
import com.spasinnya.mentoring.domain.repository.OtpRepository
import com.spasinnya.mentoring.domain.repository.PurchaseBookRepository
import com.spasinnya.mentoring.domain.repository.RegisterRepository
import com.spasinnya.mentoring.domain.repository.WeeksRepository

interface RepoModule {
    val loginRepository: LoginRepository
    val registerRepository: RegisterRepository
    val otpRepository: OtpRepository
    val logoutRepository: LogoutRepository
    val booksRepository: BooksRepository
    val purchaseBookRepository: PurchaseBookRepository
    val weeksRepository: WeeksRepository
}