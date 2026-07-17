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
            }
        }

        val question = parseRich(questionText)
        val optionParagraphs = options.map { parseRich(it) }

        val hasInlineInput = textLines.any { it.contains(INPUT_PLACEHOLDER) }
        val description = textLines
            .takeIf { it.isNotEmpty() && !hasInlineInput }
            ?.joinToString("\n\n") { cleanText(it) }
            ?.let { parseRich(it) }

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
     * becomes an [HomeworkTextSegment.Input] with a running index; everything else
     * becomes static [HomeworkTextSegment.Text].
     */
    private fun parseSegments(textLines: List<String>): List<HomeworkTextSegment> {
        val segments = mutableListOf<HomeworkTextSegment>()
        var inputIndex = 0

        textLines.forEach { line ->
            val parts = cleanText(line).split(INPUT_PLACEHOLDER)
            parts.forEachIndexed { index, part ->
                val text = part.trim()
                if (text.isNotEmpty()) {
                    segments += HomeworkTextSegment.Text(parseRich(text))
                }
                if (index < parts.lastIndex) {
                    segments += HomeworkTextSegment.Input(inputIndex)
                    inputIndex++
                }
            }
        }

        return segments
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
        const val CENTER_PREFIX = "center:"

        val weekHeaderRegex = Regex("""(?m)^#\s+Week\s+(\d+)\s*$""")
        val lessonHeaderRegex = Regex("""(?m)^#\s+Lesson\s+(\d+)\s*$""")
        val questionHeaderRegex = Regex("""(?m)^##\s+Question:\s*(.+?)\s*$""")
        val typeRegex = Regex("""(?i)^Type:\s*(.+?)\s*$""")
        val optionRegex = Regex("""^□\s*(.+?)\s*$""")
        val textRegex = Regex("""(?i)^Text:\s*(.+?)\s*$""")
    }
}
