package com.spasinnya.mentoring.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TokenApiResponse(
    val accessToken: String,
    val accessExpiresAt: String,
    val refreshToken: String,
    val refreshExpiresAt: String
)
