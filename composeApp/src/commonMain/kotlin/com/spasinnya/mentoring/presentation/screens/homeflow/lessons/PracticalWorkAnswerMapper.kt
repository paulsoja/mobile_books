package com.spasinnya.mentoring.presentation.screens.homeflow.lessons

import com.spasinnya.mentoring.domain.model.HomeworkAnswer
import com.spasinnya.mentoring.domain.model.HomeworkAnswerValue
import com.spasinnya.mentoring.domain.model.HomeworkLesson
import com.spasinnya.mentoring.domain.model.HomeworkQuestion
import com.spasinnya.mentoring.domain.model.HomeworkTextSegment

/**
 * Bridges the UI answer maps used by [PracticalWorkModalBottomSheet]
 * (checkedOptions / selectedOptions / textAnswers) and the server
 * [HomeworkAnswer] payload.
 *
 * The mapping is symmetric so that a GET → fill → PUT round trip is lossless:
 * each [HomeworkAnswer.questionId] is a question id, and every
 * [HomeworkAnswerValue.id] is the question-relative sub-key.
 */
private const val INPUT_ID = "__input__"
private const val SELECTED_ID = "__selected__"

/**
 * Builds the request payload for a single lesson from the current UI state.
 * Empty selections/inputs are serialized as empty strings.
 */
fun buildHomeworkAnswers(
    lesson: HomeworkLesson,
    checkedOptions: Map<String, Boolean>,
    selectedOptions: Map<String, String>,
    textAnswers: Map<String, String>,
): List<HomeworkAnswer> =
    lesson.questions.mapNotNull { question ->
        val qid = question.id
        val values: List<HomeworkAnswerValue> = when (question) {
            is HomeworkQuestion.Checkbox -> question.options.map { option ->
                HomeworkAnswerValue(
                    id = option.id,
                    answer = checkedOptions["${qid}_${option.id}"].asAnswer(),
                )
            }

            is HomeworkQuestion.CheckboxInput -> question.options.map { option ->
                HomeworkAnswerValue(
                    id = option.id,
                    answer = checkedOptions["${qid}_${option.id}"].asAnswer(),
                )
            } + HomeworkAnswerValue(
                id = INPUT_ID,
                answer = textAnswers[qid].orEmpty(),
            )

            is HomeworkQuestion.RadioButton -> listOf(
                HomeworkAnswerValue(
                    id = SELECTED_ID,
                    answer = selectedOptions[qid]?.removePrefix("${qid}_").orEmpty(),
                )
            )

            is HomeworkQuestion.Input -> listOf(
                HomeworkAnswerValue(id = INPUT_ID, answer = textAnswers[qid].orEmpty())
            )

            is HomeworkQuestion.TextInput -> question.segments.mapNotNull { segment ->
                val index = when (segment) {
                    is HomeworkTextSegment.Input -> segment.index
                    is HomeworkTextSegment.NumberInput -> segment.index
                    is HomeworkTextSegment.Text -> return@mapNotNull null
                }
                HomeworkAnswerValue(
                    id = "i$index",
                    answer = textAnswers["${qid}_i$index"].orEmpty(),
                )
            }

            is HomeworkQuestion.TextRadioButton -> question.items.mapIndexed { rowIndex, _ ->
                val rowId = "${qid}_r$rowIndex"
                HomeworkAnswerValue(
                    id = "r$rowIndex",
                    answer = selectedOptions[rowId]?.removePrefix("${rowId}_").orEmpty(),
                )
            }

            is HomeworkQuestion.Text,
            is HomeworkQuestion.Open -> return@mapNotNull null
        }

        HomeworkAnswer(questionId = qid, values = values)
    }

/**
 * Result of parsing server answers back into the three UI maps.
 */
data class HomeworkAnswerState(
    val checkedOptions: Map<String, Boolean> = emptyMap(),
    val selectedOptions: Map<String, String> = emptyMap(),
    val textAnswers: Map<String, String> = emptyMap(),
)

/**
 * Parses the server answers for a lesson back into the UI maps, reversing
 * [buildHomeworkAnswers].
 */
fun List<HomeworkAnswer>.toHomeworkAnswerState(lesson: HomeworkLesson): HomeworkAnswerState {
    val byQuestion = associateBy { it.questionId }
    val checked = mutableMapOf<String, Boolean>()
    val selected = mutableMapOf<String, String>()
    val text = mutableMapOf<String, String>()

    lesson.questions.forEach { question ->
        val qid = question.id
        val values = byQuestion[qid]?.values ?: return@forEach

        when (question) {
            is HomeworkQuestion.Checkbox -> values.forEach { value ->
                checked["${qid}_${value.id}"] = value.answer.isChecked()
            }

            is HomeworkQuestion.CheckboxInput -> values.forEach { value ->
                if (value.id == INPUT_ID) {
                    text[qid] = value.answer
                } else {
                    checked["${qid}_${value.id}"] = value.answer.isChecked()
                }
            }

            is HomeworkQuestion.RadioButton -> values
                .firstOrNull { it.id == SELECTED_ID }
                ?.answer
                ?.takeIf { it.isNotBlank() }
                ?.let { selected[qid] = "${qid}_$it" }

            is HomeworkQuestion.Input -> values
                .firstOrNull { it.id == INPUT_ID }
                ?.let { text[qid] = it.answer }

            is HomeworkQuestion.TextInput -> values.forEach { value ->
                // value.id == "i{index}", state key == "{qid}_i{index}"
                text["${qid}_${value.id}"] = value.answer
            }

            is HomeworkQuestion.TextRadioButton -> values.forEach { value ->
                // value.id == "r{rowIndex}", answer == "o{optionIndex}"
                if (value.answer.isNotBlank()) {
                    val rowId = "${qid}_${value.id}"
                    selected[rowId] = "${rowId}_${value.answer}"
                }
            }

            is HomeworkQuestion.Text,
            is HomeworkQuestion.Open -> Unit
        }
    }

    return HomeworkAnswerState(
        checkedOptions = checked,
        selectedOptions = selected,
        textAnswers = text,
    )
}

private fun Boolean?.asAnswer(): String = if (this == true) "true" else ""

private fun String.isChecked(): Boolean = equals("true", ignoreCase = true)
