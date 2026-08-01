package com.spasinnya.mentoring.data.mapper

import com.spasinnya.mentoring.data.model.HomeworkAnswerApi
import com.spasinnya.mentoring.data.model.HomeworkAnswersApiRequest
import com.spasinnya.mentoring.data.model.HomeworkAnswersApiResponse
import com.spasinnya.mentoring.data.model.HomeworkValueApi
import com.spasinnya.mentoring.domain.model.HomeworkAnswer
import com.spasinnya.mentoring.domain.model.HomeworkAnswerValue

fun HomeworkAnswersApiResponse.toDomain(): List<HomeworkAnswer> =
    answers.map { answer ->
        HomeworkAnswer(
            questionId = answer.questionId,
            values = answer.values.map { HomeworkAnswerValue(id = it.id, answer = it.answer) }
        )
    }

fun List<HomeworkAnswer>.toApiRequest(): HomeworkAnswersApiRequest =
    HomeworkAnswersApiRequest(
        answers = map { answer ->
            HomeworkAnswerApi(
                questionId = answer.questionId,
                values = answer.values.map { HomeworkValueApi(id = it.id, answer = it.answer) }
            )
        }
    )
