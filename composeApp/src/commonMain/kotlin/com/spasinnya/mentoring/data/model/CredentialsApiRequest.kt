package com.spasinnya.mentoring.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CredentialsApiRequest(
    val email: String,
    val password: String
)
