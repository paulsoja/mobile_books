package com.spasinnya.mentoring.domain.usecase.auth

import com.spasinnya.mentoring.data.mapper.toData
import com.spasinnya.mentoring.domain.model.Credentials
import com.spasinnya.mentoring.domain.model.Token
import com.spasinnya.mentoring.domain.repository.LoginRepository
import com.spasinnya.mentoring.domain.rules.DomainResult
import kotlinx.coroutines.flow.Flow

typealias LoginUseCase = suspend (Credentials) -> Flow<DomainResult<Token>>

fun loginUseCase(repository: LoginRepository): LoginUseCase = { credentials ->
    repository(credentials.toData())
}