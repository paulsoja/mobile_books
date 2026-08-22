package com.spasinnya.mentoring.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class HomeworkProgress(
    val week_number: Int,
    val completed: Int,
)
