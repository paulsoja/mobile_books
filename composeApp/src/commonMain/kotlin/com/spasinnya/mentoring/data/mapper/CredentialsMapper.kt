package com.spasinnya.mentoring.data.mapper

import com.spasinnya.mentoring.data.model.CredentialsApiRequest
import com.spasinnya.mentoring.domain.model.Credentials

fun Credentials.toData() = CredentialsApiRequest(
    email = email.value,
    password = password.value
)