package com.spasinnya.mentoring.domain.usecase.settings

import com.spasinnya.mentoring.domain.enums.Language
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.repository.PrefRepository
import kotlinx.coroutines.flow.Flow

class SetAppLocaleUseCase(
    private val prefRepository: PrefRepository
) {
    operator fun invoke(language: Language): Flow<DomainResult<Unit>> =
        prefRepository.setLocale(language.tag)
}
