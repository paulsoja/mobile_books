package com.spasinnya.mentoring.domain.usecase.settings

import com.spasinnya.mentoring.domain.enums.Language
import com.spasinnya.mentoring.domain.repository.LocaleRepository
import com.spasinnya.mentoring.domain.rules.DomainResult
import com.spasinnya.mentoring.presentation.base.Validated
import com.spasinnya.mentoring.presentation.base.fold
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

typealias GetAppLocaleUseCase = suspend () -> Flow<DomainResult<Language>>

fun appLocaleUseCase(repository: LocaleRepository): GetAppLocaleUseCase = {
    repository.invoke().map { result ->
        result.fold(
            onValid = { Validated.Valid(Language.fromTag(it.value)) },
            onInvalid = { Validated.Valid(Language.System) }
        )
    }
}