package com.spasinnya.mentoring.domain.usecase.auth

import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.repository.PrefRepository
import kotlinx.coroutines.flow.Flow

class ChangeCongratsShownStatusUseCase(
    private val prefRepository: PrefRepository
) {
    operator fun invoke(): Flow<DomainResult<Unit>> =
        prefRepository.changeCongratsShown()
}
