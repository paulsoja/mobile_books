package com.spasinnya.mentoring.domain.models

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
value class AuthToken(
    val token: String
)
