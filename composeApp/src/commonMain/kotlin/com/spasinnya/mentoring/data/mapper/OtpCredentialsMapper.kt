package com.spasinnya.mentoring.data.mapper

import com.spasinnya.mentoring.data.model.OtpCredentialsApiRequest
import com.spasinnya.mentoring.domain.model.OtpCredentials

fun OtpCredentials.toData() = OtpCredentialsApiRequest(
    email = email.value,
    code = code.value
)