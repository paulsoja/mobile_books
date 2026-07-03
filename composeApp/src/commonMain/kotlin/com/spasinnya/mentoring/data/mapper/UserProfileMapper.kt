package com.spasinnya.mentoring.data.mapper

import com.spasinnya.mentoring.data.model.UserProfileApiResponse
import com.spasinnya.mentoring.domain.model.UserProfile

fun UserProfileApiResponse.toDomain() = UserProfile(
    userId = userId,
    email = email,
    role = role,
    firstName = firstName,
    lastName = lastName,
    avatarUrl = avatarUrl,
)
