package com.spasinnya.mentoring.domain.usecase.auth

import com.spasinnya.mentoring.domain.model.AuthSteps
import com.spasinnya.mentoring.domain.model.DomainError
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.repository.PrefRepository
import com.spasinnya.mentoring.presentation.base.Validated
import com.spasinnya.mentoring.presentation.base.fold
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip

class CheckAuthStepsUseCase(
    private val prefRepository: PrefRepository,
) {
    operator fun invoke(): Flow<DomainResult<AuthSteps>> =
        prefRepository.getToken().zip(prefRepository.getCongratsShown()) { token, shown ->
            shown.fold(
                onValid = {
                    when {
                        it.value && token is Validated.Valid -> Validated.Valid(AuthSteps.Home)
                        it.value.not() && token is Validated.Valid -> Validated.Valid(AuthSteps.Congrats)
                        else -> Validated.Valid(AuthSteps.Auth)
                    }
                },
                onInvalid = {
                    Validated.Invalid(DomainError.Unknown)
                }
            )
        }
}
