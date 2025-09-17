package com.spasinnya.mentoring.data.model

import kotlinx.serialization.Serializable

@Serializable
data class OtpCredentialsApiRequest(
    val email: String,
    val code: String
)
