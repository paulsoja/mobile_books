@file:OptIn(ExperimentalTime::class)

package com.spasinnya.mentoring.domain.usecase.auth

import com.spasinnya.mentoring.domain.model.AuthSteps
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.Token
import com.spasinnya.mentoring.domain.repository.PrefRepository
import com.spasinnya.mentoring.presentation.base.Validated
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class CheckAuthStepsUseCase(
    private val prefRepository: PrefRepository,
) {
    operator fun invoke(): Flow<DomainResult<AuthSteps>> =
        prefRepository.getToken().map { token ->
            Validated.Valid(if (token.isUsable()) AuthSteps.Home else AuthSteps.Auth)
        }
}

private fun DomainResult<Token>.isUsable(): Boolean =
    this is Validated.Valid && value.refreshExpiresAt > Clock.System.now()
