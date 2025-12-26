package com.spasinnya.mentoring.data.model

import kotlinx.serialization.Serializable

@Serializable
data class OtpEmailApiRequest(
    val email: String
)
