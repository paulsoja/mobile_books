package com.spasinnya.mentoring.domain.model

/**
 * A parsed homework week (`homework_week{N}.md`).
 */
data class ParsedHomeworkWeek(
    val weekNumber: Int,
    val lessons: List<HomeworkLesson>,
)

data class HomeworkLesson(
    val lessonNumber: Int,
    val questions: List<HomeworkQuestion>,
)

/**
 * A single homework question. The concrete type maps directly to a question
 * composable in `presentation.designsystem.composable.question`.
 *
 * Prose fields are [RichParagraph]s so inline formatting tags (`<b>`, `<i>`,
 * `<u>`, `<mark>`) parsed from the markdown are preserved and rendered.
 */
sealed interface HomeworkQuestion {
    val id: String
    val question: RichParagraph

    /** `Type: checkbox` -> CoreQuestionCheckbox */
    data class Checkbox(
        override val id: String,
        override val question: RichParagraph,
        val options: List<RichParagraph>,
        val description: RichParagraph? = null,
    ) : HomeworkQuestion

    /** `Type: checkbox_input` -> CoreQuestionCheckboxInput */
    data class CheckboxInput(
        override val id: String,
        override val question: RichParagraph,
        val options: List<RichParagraph>,
        val description: RichParagraph? = null,
    ) : HomeworkQuestion

    /** `Type: input` -> CoreQuestionInput */
    data class Input(
        override val id: String,
        override val question: RichParagraph,
        val description: RichParagraph? = null,
    ) : HomeworkQuestion

    /** `Type: text_input` -> CoreQuestionTextInput (text with inline blanks) */
    data class TextInput(
        override val id: String,
        override val question: RichParagraph,
        val segments: List<HomeworkTextSegment>,
    ) : HomeworkQuestion

    /** `Type: text` -> CoreQuestion with body paragraphs (display only) */
    data class Text(
        override val id: String,
        override val question: RichParagraph,
        val paragraphs: List<RichParagraph>,
    ) : HomeworkQuestion

    /** `Type: question` -> CoreQuestion (prompt only, no answer field) */
    data class Open(
        override val id: String,
        override val question: RichParagraph,
        val description: RichParagraph? = null,
    ) : HomeworkQuestion
}

/**
 * Segment of a [HomeworkQuestion.TextInput] body: either static text or a blank
 * the user fills in (`<input>` placeholder in markdown).
 */
sealed interface HomeworkTextSegment {
    data class Text(val text: RichParagraph) : HomeworkTextSegment
    data class Input(val index: Int) : HomeworkTextSegment
}
