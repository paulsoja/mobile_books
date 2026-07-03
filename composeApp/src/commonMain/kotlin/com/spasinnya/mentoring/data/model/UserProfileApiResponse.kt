package com.spasinnya.mentoring.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserProfileApiResponse(
    val userId: Long,
    val email: String,
    val role: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val avatarUrl: String? = null,
)
