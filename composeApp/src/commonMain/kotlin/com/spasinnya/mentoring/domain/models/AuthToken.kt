package com.spasinnya.mentoring.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class AuthToken(
    val token: String
)
