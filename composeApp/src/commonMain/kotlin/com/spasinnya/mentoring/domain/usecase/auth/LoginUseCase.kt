package com.spasinnya.mentoring.domain.usecase.auth

import com.spasinnya.mentoring.data.mapper.toData
import com.spasinnya.mentoring.domain.model.AuthToken
import com.spasinnya.mentoring.domain.model.Credentials
import com.spasinnya.mentoring.domain.repository.LoginRepository

typealias LoginUseCase = suspend (Credentials) -> AuthToken

fun loginUseCase(repository: LoginRepository): LoginUseCase = { credentials ->
    repository(credentials.toData()) ?: throw IllegalArgumentException("Invalid login credentials")
}