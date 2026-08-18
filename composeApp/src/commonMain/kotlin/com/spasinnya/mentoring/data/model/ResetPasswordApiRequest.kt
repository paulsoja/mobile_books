package com.spasinnya.mentoring.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordApiRequest(
    val email: String,
    val code: String,
    val newPassword: String
)
