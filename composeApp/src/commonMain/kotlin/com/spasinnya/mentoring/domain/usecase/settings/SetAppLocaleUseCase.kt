package com.spasinnya.mentoring.domain.usecase.settings

import com.spasinnya.mentoring.domain.enums.Language
import com.spasinnya.mentoring.domain.repository.SetLocaleRepository
import com.spasinnya.mentoring.domain.rules.DomainResult
import kotlinx.coroutines.flow.Flow

typealias SetAppLocaleUseCase = suspend (Language) -> Flow<DomainResult<Unit>>

fun changeAppLocaleUseCase(repository: SetLocaleRepository): SetAppLocaleUseCase = { language ->
    repository.invoke(language.tag)
}