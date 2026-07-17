package com.spasinnya.mentoring.data.parser

import com.spasinnya.mentoring.domain.model.LessonBlock
import com.spasinnya.mentoring.domain.model.LessonContent
import com.spasinnya.mentoring.domain.model.ParsedLesson
import com.spasinnya.mentoring.domain.model.ParsedWeek
import com.spasinnya.mentoring.domain.model.RichParagraph

class MentorshipMarkdownParser {

    fun parseWeek(markdown: String): ParsedWeek {
        val normalized = normalizeMarkdown(markdown)
        require(normalized.isNotBlank())

        val weekNumber = extractWeekNumber(normalized)
        val weekTitle = extractWeekTitle(normalized)

        val lessonMatches = lessonHeaderRegex.findAll(normalized).toList()
        require(lessonMatches.isNotEmpty())

        val lessons = lessonMatches.mapIndexed { index, lessonMatch ->
            val lessonNumber = lessonMatch.groupValues[1].toInt()
            val lessonTitle = lessonMatch.groupValues[2].trim()

            val bodyStart = lessonMatch.range.last + 1
            val bodyEnd = lessonMatches.getOrNull(index + 1)?.range?.first ?: normalized.length
            val rawLessonContent = normalized.substring(bodyStart, bodyEnd).trim()

            val parsedLessonContent = parseLessonContent(rawLessonContent)

            ParsedLesson(
                lessonNumber = lessonNumber,
                title = lessonTitle,
                quotes = parsedLessonContent.quotes,
                blocks = parsedLessonContent.body,
            )
        }.sortedBy { it.lessonNumber }

        return ParsedWeek(
            weekNumber = weekNumber,
            weekTitle = weekTitle,
            lessons = lessons,
        )
    }

    private fun parseLessonContent(content: String): LessonContent {
        val lines = content.lines()

        val quotes = mutableListOf<RichParagraph>()
        var index = 0

        while (index < lines.size && lines[index].isBlank()) {
            index++
        }

        while (index < lines.size) {
            val line = lines[index].trim()

            if (line.startsWith(">")) {
                quotes += parseRichParagraph(
                    line.removePrefix(">").trim()
                )
                index++
                continue
            }

            if (line.isBlank()) {
                index++
                continue
            }

            break
        }

        val body = parseBodyBlocks(
            lines.drop(index).joinToString("\n").trim()
        )

        return LessonContent(
            quotes = quotes,
            body = body,
        )
    }

    private fun parseBodyBlocks(content: String): List<LessonBlock> {
        val lines = content.lines()
        val blocks = mutableListOf<LessonBlock>()
        val paragraphBuffer = mutableListOf<String>()

        fun flushParagraphBuffer() {
            if (paragraphBuffer.isEmpty()) return

            val paragraphText = paragraphBuffer.joinToString("\n").trim()
            if (paragraphText.isNotBlank()) {
                blocks += LessonBlock.Paragraph(
                    paragraph = parseRichParagraph(paragraphText)
                )
            }

            paragraphBuffer.clear()
        }

        var index = 0
        while (index < lines.size) {
            val rawLine = lines[index]
            val line = rawLine.trim()

            when {
                line.isBlank() -> {
                    flushParagraphBuffer()
                    index++
                }

                line == "---" -> {
                    flushParagraphBuffer()
                    blocks += LessonBlock.Divider
                    index++
                }

                line.equals("table", ignoreCase = true) -> {
                    flushParagraphBuffer()
                    blocks += LessonBlock.Table
                    index++
                }

                line.startsWith("image:") -> {
                    flushParagraphBuffer()
                    val path = line.removePrefix("image:").trim()
                    if (path.isNotBlank()) {
                        blocks += LessonBlock.Image(path)
                    }
                    index++
                }

                line.startsWith("center:") -> {
                    flushParagraphBuffer()

                    val text = line.removePrefix("center:").trim()
                    if (text.isNotBlank()) {
                        blocks += LessonBlock.CenterText(
                            paragraph = parseRichParagraph(text)
                        )
                    }

                    index++
                }

                else -> {
                    paragraphBuffer += rawLine
                    index++
                }
            }
        }

        flushParagraphBuffer()

        return blocks
    }

    private fun parseRichParagraph(text: String): RichParagraph =
        InlineMarkdownParser.parseParagraph(text)

    private fun extractWeekNumber(markdown: String): Int {
        frontMatterWeekNumberRegex.find(markdown)?.let {
            return it.groupValues[1].toInt()
        }

        englishWeekHeaderRegex.find(markdown)?.let {
            return it.groupValues[1].toInt()
        }

        error("Week number not found")
    }

    private fun extractWeekTitle(markdown: String): String {
        frontMatterWeekTitleRegex.find(markdown)?.let {
            return it.groupValues[1].trim()
        }

        englishWeekHeaderRegex.find(markdown)?.let {
            return it.groupValues[2].trim()
        }

        error("Week title not found")
    }

    private fun normalizeMarkdown(markdown: String): String =
        markdown
            .replace("\r\n", "\n")
            .replace("\r", "\n")
            .trim()

    private companion object {
        val frontMatterWeekNumberRegex = Regex("""(?m)^weekNumber:\s*(\d+)\s*$""")
        val frontMatterWeekTitleRegex = Regex("""(?m)^weekTitle:\s*(.+?)\s*$""")
        val englishWeekHeaderRegex = Regex("""(?m)^#\s+Week\s+(\d+)\s+Subject:\s+(.+?)\s*$""")
        val lessonHeaderRegex = Regex("""(?m)^##\s+Lesson\s+(\d+)\s+(.+?)\s*$""")
    }
}