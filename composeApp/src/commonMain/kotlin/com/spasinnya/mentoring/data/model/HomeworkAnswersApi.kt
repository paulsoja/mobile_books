package com.spasinnya.mentoring.data.model

import kotlinx.serialization.EncodeDefault
import kotlinx.serialization.ExperimentalSerializationApi
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
@OptIn(ExperimentalSerializationApi::class)
data class HomeworkAnswerApi(
    val questionId: String,
    @EncodeDefault(EncodeDefault.Mode.NEVER)
    val values: List<HomeworkValueApi> = emptyList()
)

@Serializable
data class HomeworkValueApi(
    val id: String,
    val answer: String
)
