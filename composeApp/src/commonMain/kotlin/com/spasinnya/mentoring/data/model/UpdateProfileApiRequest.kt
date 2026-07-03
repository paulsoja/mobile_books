package com.spasinnya.mentoring.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileApiRequest(
    val firstName: String? = null,
    val lastName: String? = null,
)
