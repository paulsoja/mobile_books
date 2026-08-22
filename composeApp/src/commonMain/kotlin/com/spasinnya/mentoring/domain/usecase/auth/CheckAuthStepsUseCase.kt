@file:OptIn(ExperimentalTime::class)

package com.spasinnya.mentoring.domain.usecase.auth

import com.spasinnya.mentoring.domain.model.AuthSteps
import com.spasinnya.mentoring.domain.model.DomainError
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.Token
import com.spasinnya.mentoring.domain.repository.PrefRepository
import com.spasinnya.mentoring.presentation.base.Validated
import com.spasinnya.mentoring.presentation.base.fold
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class CheckAuthStepsUseCase(
    private val prefRepository: PrefRepository,
) {
    operator fun invoke(): Flow<DomainResult<AuthSteps>> =
        prefRepository.getToken().combine(prefRepository.getCongratsShown()) { token, shown ->
            shown.fold(
                onValid = {
                    when {
                        token.isUsable().not() -> Validated.Valid(AuthSteps.Auth)
                        it.value -> Validated.Valid(AuthSteps.Home)
                        else -> Validated.Valid(AuthSteps.Congrats)
                    }
                },
                onInvalid = {
                    Validated.Invalid(DomainError.Unknown)
                }
            )
        }
}

private fun DomainResult<Token>.isUsable(): Boolean =
    this is Validated.Valid && value.refreshExpiresAt > Clock.System.now()
