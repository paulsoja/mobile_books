package com.spasinnya.mentoring.domain.model

/**
 * Domain representation of a single question's saved answer within a lesson.
 *
 * [values] mirrors the per-question payload stored server-side, e.g.
 * [{"id":"lord","answer":""}] or [{"id":"name","answer":"John"}].
 */
data class HomeworkAnswer(
    val questionId: String,
    val values: List<HomeworkAnswerValue>
)

data class HomeworkAnswerValue(
    val id: String,
    val answer: String
)
