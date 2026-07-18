package com.spasinnya.mentoring.data.parser

import com.spasinnya.mentoring.domain.model.HomeworkLesson
import com.spasinnya.mentoring.domain.model.HomeworkQuestion
import com.spasinnya.mentoring.domain.model.HomeworkTextSegment
import com.spasinnya.mentoring.domain.model.ParsedHomeworkWeek
import com.spasinnya.mentoring.domain.model.RichParagraph

/**
 * Parses the homework markdown files stored under
 * `files/homeworks/{bookId}/homework_week{N}.md`.
 *
 * Modeled on [MentorshipMarkdownParser]. The homework format is:
 *
 * ```
 * # Week 1
 * # Lesson 1
 * ## Question:  <prompt>
 * Type: checkbox
 * □ option one;
 * □ option two;
 *
 * ---
 *
 * # Lesson 2
 * ## Question:  <prompt>
 * Type: text_input
 * Text: «Адже <input> й позбавлені Божої слави»
 * ```
 */
class HomeworkMarkdownParser {

    fun parseWeek(markdown: String): ParsedHomeworkWeek {
        val normalized = normalizeMarkdown(markdown)
        require(normalized.isNotBlank())

        val weekNumber = extractWeekNumber(normalized)

        val lessonMatches = lessonHeaderRegex.findAll(normalized).toList()
        require(lessonMatches.isNotEmpty())

        val lessons = lessonMatches.mapIndexed { index, lessonMatch ->
            val lessonNumber = lessonMatch.groupValues[1].toInt()

            val bodyStart = lessonMatch.range.last + 1
            val bodyEnd = lessonMatches.getOrNull(index + 1)?.range?.first ?: normalized.length
            val rawLessonBody = normalized.substring(bodyStart, bodyEnd).trim()

            HomeworkLesson(
                lessonNumber = lessonNumber,
                questions = parseQuestions(weekNumber, lessonNumber, rawLessonBody),
            )
        }.sortedBy { it.lessonNumber }

        return ParsedHomeworkWeek(
            weekNumber = weekNumber,
            lessons = lessons,
        )
    }

    private fun parseQuestions(
        weekNumber: Int,
        lessonNumber: Int,
        lessonBody: String,
    ): List<HomeworkQuestion> {
        val questionMatches = questionHeaderRegex.findAll(lessonBody).toList()
        if (questionMatches.isEmpty()) return emptyList()

        return questionMatches.mapIndexed { index, questionMatch ->
            val questionText = questionMatch.groupValues[1].trim()

            val contentStart = questionMatch.range.last + 1
            val contentEnd = questionMatches.getOrNull(index + 1)?.range?.first ?: lessonBody.length
            val rawContent = lessonBody.substring(contentStart, contentEnd).trim()

            buildQuestion(
                id = "w${weekNumber}_l${lessonNumber}_q$index",
                questionText = questionText,
                content = rawContent,
            )
        }
    }

    private fun buildQuestion(
        id: String,
        questionText: String,
        content: String,
    ): HomeworkQuestion {
        var type: String? = null
        val options = mutableListOf<String>()
        val textLines = mutableListOf<String>()
        val bodyLines = mutableListOf<String>()

        content.lines().forEach { rawLine ->
            val line = rawLine.trim()
            when {
                line.isBlank() || line == "---" -> Unit

                typeRegex.matches(line) ->
                    type = typeRegex.find(line)?.groupValues?.get(1)?.trim()?.lowercase()

                optionRegex.matches(line) ->
                    optionRegex.find(line)?.groupValues?.get(1)?.let { option ->
                        options += option.trim().trimEnd(';').trim()
                    }

                textRegex.matches(line) ->
                    textRegex.find(line)?.groupValues?.get(1)?.let { text ->
                        textLines += text.trim()
                    }

                else -> bodyLines += line
            }
        }

        val question = parseRich(questionText)
        val optionParagraphs = options.map { parseRich(it) }

        val hasInlineInput = textLines.any {
            it.contains(INPUT_PLACEHOLDER) || it.contains(NUMBER_INPUT_PLACEHOLDER)
        }
        val description = textLines
            .takeIf { it.isNotEmpty() && !hasInlineInput }
            ?.joinToString("\n\n") { cleanText(it) }
            ?.let { parseRich(it) }

        if (type == "text_radiobutton") {
            val items = parseRadioItems(bodyLines)
            if (items.isNotEmpty()) {
                return HomeworkQuestion.TextRadioButton(
                    id = id,
                    question = question,
                    items = items,
                )
            }
        }

        return when {
            options.isNotEmpty() && type == "checkbox_input" -> HomeworkQuestion.CheckboxInput(
                id = id,
                question = question,
                options = optionParagraphs,
                description = description,
            )

            options.isNotEmpty() && type == "radiobutton" -> HomeworkQuestion.RadioButton(
                id = id,
                question = question,
                options = optionParagraphs,
                description = description,
            )

            options.isNotEmpty() -> HomeworkQuestion.Checkbox(
                id = id,
                question = question,
                options = optionParagraphs,
                description = description,
            )

            hasInlineInput -> HomeworkQuestion.TextInput(
                id = id,
                question = question,
                segments = parseSegments(textLines),
            )

            type == "text" && textLines.isNotEmpty() -> HomeworkQuestion.Text(
                id = id,
                question = question,
                paragraphs = textLines.map { parseRich(cleanText(it)) },
            )

            type == "question" -> HomeworkQuestion.Open(
                id = id,
                question = question,
                description = description,
            )

            type == "input" -> HomeworkQuestion.Input(
                id = id,
                question = question,
                description = description,
            )

            textLines.isNotEmpty() -> HomeworkQuestion.Text(
                id = id,
                question = question,
                paragraphs = textLines.map { parseRich(cleanText(it)) },
            )

            else -> HomeworkQuestion.Input(
                id = id,
                question = question,
                description = description,
            )
        }
    }

    /**
     * Splits the `Text:` lines into rendered segments. Each `<input>` placeholder
     * becomes an [HomeworkTextSegment.Input] and each `<input_number>` becomes an
     * [HomeworkTextSegment.NumberInput], both sharing one running index so their
     * answer keys stay unique; everything else becomes static
     * [HomeworkTextSegment.Text].
     */
    private fun parseSegments(textLines: List<String>): List<HomeworkTextSegment> {
        val segments = mutableListOf<HomeworkTextSegment>()
        var inputIndex = 0

        textLines.forEach { line ->
            val cleaned = cleanText(line)
            var cursor = 0

            inputTokenRegex.findAll(cleaned).forEach { match ->
                val before = cleaned.substring(cursor, match.range.first).trim()
                if (before.isNotEmpty()) {
                    segments += HomeworkTextSegment.Text(parseRich(before))
                }

                segments += if (match.value == NUMBER_INPUT_PLACEHOLDER) {
                    HomeworkTextSegment.NumberInput(inputIndex)
                } else {
                    HomeworkTextSegment.Input(inputIndex)
                }
                inputIndex++

                cursor = match.range.last + 1
            }

            val tail = cleaned.substring(cursor).trim()
            if (tail.isNotEmpty()) {
                segments += HomeworkTextSegment.Text(parseRich(tail))
            }
        }

        return segments
    }

    /**
     * Builds the rows of a `text_radiobutton` question. Each body line looks like
     * `1. <prompt>? <Так / Ні>`: the prompt is the text before the trailing
     * `<...>`, and the options are the values inside it, separated by `/`.
     * Lines without a trailing `<...>` group are ignored.
     */
    private fun parseRadioItems(bodyLines: List<String>): List<HomeworkQuestion.TextRadioButton.Item> =
        bodyLines.mapNotNull { line ->
            val match = radioRowRegex.find(line) ?: return@mapNotNull null

            val prompt = match.groupValues[1].trim()
            val options = match.groupValues[2]
                .split('/')
                .map { it.trim() }
                .filter { it.isNotEmpty() }

            if (prompt.isEmpty() || options.isEmpty()) {
                null
            } else {
                HomeworkQuestion.TextRadioButton.Item(
                    prompt = parseRich(cleanText(prompt)),
                    options = options.map { parseRich(it) },
                )
            }
        }

    /**
     * Parses inline formatting tags (`<b>`, `<i>`, `<u>`, `<mark>`) into a
     * [RichParagraph], reusing the shared [InlineMarkdownParser].
     */
    private fun parseRich(text: String): RichParagraph =
        InlineMarkdownParser.parseParagraph(text)

    private fun cleanText(text: String): String {
        val result = text.trim()
        return if (result.startsWith(CENTER_PREFIX, ignoreCase = true)) {
            result.substring(CENTER_PREFIX.length).trim()
        } else {
            result
        }
    }

    private fun extractWeekNumber(markdown: String): Int {
        weekHeaderRegex.find(markdown)?.let {
            return it.groupValues[1].toInt()
        }
        error("Week number not found")
    }

    private fun normalizeMarkdown(markdown: String): String =
        markdown
            .replace("\r\n", "\n")
            .replace("\r", "\n")
            .trim()

    private companion object {
        const val INPUT_PLACEHOLDER = "<input>"
        const val NUMBER_INPUT_PLACEHOLDER = "<input_number>"
        const val CENTER_PREFIX = "center:"

        val weekHeaderRegex = Regex("""(?m)^#\s+Week\s+(\d+)\s*$""")
        val lessonHeaderRegex = Regex("""(?m)^#\s+Lesson\s+(\d+)\s*$""")
        val questionHeaderRegex = Regex("""(?m)^##\s+Question:\s*(.+?)\s*$""")
        val typeRegex = Regex("""(?i)^Type:\s*(.+?)\s*$""")
        val optionRegex = Regex("""^□\s*(.+?)\s*$""")
        val textRegex = Regex("""(?i)^Text:\s*(.+?)\s*$""")
        val radioRowRegex = Regex("""^(.*?)<([^>]*)>\s*$""")
        val inputTokenRegex = Regex(NUMBER_INPUT_PLACEHOLDER + "|" + INPUT_PLACEHOLDER)
    }
}
