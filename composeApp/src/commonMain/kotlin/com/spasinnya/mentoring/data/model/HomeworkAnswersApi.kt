package com.spasinnya.mentoring.data.model

import kotlinx.serialization.Serializable

@Serializable
data class HomeworkAnswersApiRequest(
    val answers: List<HomeworkAnswerApi>
)

@Serializable
data class HomeworkAnswersApiResponse(
    val answers: List<HomeworkAnswerApi> = emptyList()
)

@Serializable
data class HomeworkAnswerApi(
    val questionId: String,
    val values: List<HomeworkValueApi>
)

@Serializable
data class HomeworkValueApi(
    val id: String,
    val answer: String
)
