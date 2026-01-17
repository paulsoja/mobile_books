package com.spasinnya.mentoring.domain.repository

import com.spasinnya.mentoring.domain.model.Token
import com.spasinnya.mentoring.domain.rules.DomainResult
import kotlinx.coroutines.flow.Flow

typealias TokenRepository = suspend () -> Flow<DomainResult<Token>>
typealias CongratsShownRepository = suspend () -> Flow<DomainResult<Boolean>>
typealias ChangeCongratsShownRepository = suspend () -> Flow<DomainResult<Unit>>
typealias LocaleRepository = suspend () -> Flow<DomainResult<String?>>
typealias SetLocaleRepository = suspend (String) -> Flow<DomainResult<Unit>>