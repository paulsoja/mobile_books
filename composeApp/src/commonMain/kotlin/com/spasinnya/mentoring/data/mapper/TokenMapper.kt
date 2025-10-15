@file:OptIn(ExperimentalTime::class)

package com.spasinnya.mentoring.data.mapper

import com.spasinnya.mentoring.data.model.TokenApiResponse
import com.spasinnya.mentoring.domain.model.Token
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

fun TokenApiResponse.toDomain() = Token(
    accessToken = accessToken,
    accessExpiresAt = Instant.parse(accessExpiresAt),
    refreshToken = refreshToken,
    refreshExpiresAt = Instant.parse(refreshExpiresAt)
)