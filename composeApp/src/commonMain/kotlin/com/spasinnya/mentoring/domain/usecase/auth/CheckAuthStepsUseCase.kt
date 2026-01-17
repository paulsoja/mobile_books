package com.spasinnya.mentoring.domain.usecase.auth

import com.spasinnya.mentoring.domain.model.AuthSteps
import com.spasinnya.mentoring.domain.repository.CongratsShownRepository
import com.spasinnya.mentoring.domain.repository.TokenRepository
import com.spasinnya.mentoring.domain.rules.DomainError
import com.spasinnya.mentoring.domain.rules.DomainResult
import com.spasinnya.mentoring.presentation.base.Validated
import com.spasinnya.mentoring.presentation.base.fold
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip

typealias AuthStepsUseCase = suspend () -> Flow<DomainResult<AuthSteps>>

fun checkAuthStepsUseCase(
    tokenRepository: TokenRepository,
    congratsShownRepository: CongratsShownRepository
): AuthStepsUseCase = {
    tokenRepository.invoke().zip(congratsShownRepository.invoke()) { token, shown ->
        Napier.d { "checkAuthStepsUseCase: $token, $shown" }
        shown.fold(
            onValid = {
                when {
                    it.value && token is Validated.Valid -> Validated.Valid(AuthSteps.Home)
                    it.value.not() && token is Validated.Valid -> Validated.Valid(AuthSteps.Congrats)
                    else -> Validated.Valid(AuthSteps.Auth)
                }
            },
            onInvalid = {
                Validated.Invalid(listOf(DomainError.Unknown))
            }
        )
    }
}