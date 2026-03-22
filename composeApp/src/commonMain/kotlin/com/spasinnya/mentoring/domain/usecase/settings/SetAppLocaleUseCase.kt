package com.spasinnya.mentoring.domain.usecase.settings

import com.spasinnya.mentoring.domain.enums.Language
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.repository.SetLocaleRepository
import kotlinx.coroutines.flow.Flow

typealias SetAppLocaleUseCase = suspend (Language) -> Flow<DomainResult<Unit>>

fun changeAppLocaleUseCase(repository: SetLocaleRepository): SetAppLocaleUseCase = { language ->
    repository.invoke(language.tag)
}