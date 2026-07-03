package com.spasinnya.mentoring.domain.model

data class UserProfile(
    val userId: Long,
    val email: String,
    val role: String,
    val firstName: String?,
    val lastName: String?,
    val avatarUrl: String?,
)
