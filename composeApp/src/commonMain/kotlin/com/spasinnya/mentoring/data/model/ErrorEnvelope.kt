package com.spasinnya.mentoring.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorEnvelope(
    val code: Int? = null,
    val message: String? = null,
    val statusCode: String? = null
)
