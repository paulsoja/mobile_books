package com.spasinnya.mentoring.domain.usecase.profile

import com.spasinnya.mentoring.domain.repository.AuthRepository

class DeleteAccountUseCase(private val authRepository: AuthRepository) {
    operator fun invoke() = authRepository.deleteAccount()
}
