package com.spasinnya.mentoring.domain.usecase.settings

import com.spasinnya.mentoring.domain.enums.Language
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.repository.PrefRepository
import com.spasinnya.mentoring.presentation.base.Validated
import com.spasinnya.mentoring.presentation.base.fold
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAppLocaleUseCase(
    private val prefRepository: PrefRepository
) {
    operator fun invoke(): Flow<DomainResult<Language>> =
        prefRepository.getLocale().map { result ->
            result.fold(
                onValid = { Validated.Valid(Language.fromTag(it.value)) },
                onInvalid = { Validated.Valid(Language.System) }
            )
        }
}
