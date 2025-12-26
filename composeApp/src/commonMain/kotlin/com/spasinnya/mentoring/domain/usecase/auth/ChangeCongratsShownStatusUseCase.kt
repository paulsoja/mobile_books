package com.spasinnya.mentoring.domain.usecase.auth

import com.spasinnya.mentoring.domain.repository.ChangeCongratsShownRepository
import com.spasinnya.mentoring.domain.rules.DomainResult
import kotlinx.coroutines.flow.Flow

typealias ChangeCongratsShownStatusUseCase = suspend () -> Flow<DomainResult<Unit>>

fun changeCongratsShownStatusUseCase(
    changeCongratsShownRepository: ChangeCongratsShownRepository
): ChangeCongratsShownStatusUseCase = {
    changeCongratsShownRepository.invoke()
}