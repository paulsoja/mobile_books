package com.spasinnya.mentoring.domain.model

data class ParsedHomeworkWeek(
    val weekNumber: Int,
    val lessons: List<HomeworkLesson>,
)

data class HomeworkLesson(
    val lessonNumber: Int,
    val questions: List<HomeworkQuestion>,
)

sealed interface HomeworkQuestion {
    val id: String
    val question: RichParagraph

    data class Checkbox(
        override val id: String,
        override val question: RichParagraph,
        val options: List<HomeworkOption>,
        val description: RichParagraph? = null,
    ) : HomeworkQuestion

    data class RadioButton(
        override val id: String,
        override val question: RichParagraph,
        val options: List<HomeworkOption>,
        val description: RichParagraph? = null,
    ) : HomeworkQuestion

    data class CheckboxInput(
        override val id: String,
        override val question: RichParagraph,
        val options: List<HomeworkOption>,
        val description: RichParagraph? = null,
    ) : HomeworkQuestion

    data class Input(
        override val id: String,
        override val question: RichParagraph,
        val description: RichParagraph? = null,
    ) : HomeworkQuestion

    data class TextInput(
        override val id: String,
        override val question: RichParagraph,
        val segments: List<HomeworkTextSegment>,
    ) : HomeworkQuestion

    data class Text(
        override val id: String,
        override val question: RichParagraph,
        val paragraphs: List<RichParagraph>,
    ) : HomeworkQuestion

    data class Open(
        override val id: String,
        override val question: RichParagraph,
        val description: RichParagraph? = null,
    ) : HomeworkQuestion

    data class TextRadioButton(
        override val id: String,
        override val question: RichParagraph,
        val items: List<Item>,
    ) : HomeworkQuestion {
        data class Item(
            val prompt: RichParagraph,
            val options: List<RichParagraph>,
        )
    }
}

data class HomeworkOption(
    val id: String,
    val text: RichParagraph,
)

sealed interface HomeworkTextSegment {
    data class Text(val text: RichParagraph) : HomeworkTextSegment
    data class Input(val index: Int) : HomeworkTextSegment
    data class NumberInput(val index: Int) : HomeworkTextSegment
}
