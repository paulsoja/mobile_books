package com.spasinnya.mentoring.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenApiRequest(
    val refreshToken: String
)
