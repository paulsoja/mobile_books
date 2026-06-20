package com.spasinnya.mentoring.data.parser

import com.spasinnya.mentoring.domain.model.LessonBlock
import com.spasinnya.mentoring.domain.model.LessonContent
import com.spasinnya.mentoring.domain.model.ParsedLesson
import com.spasinnya.mentoring.domain.model.ParsedWeek
import com.spasinnya.mentoring.domain.model.RichParagraph
import com.spasinnya.mentoring.domain.model.TextSpan
import com.spasinnya.mentoring.domain.model.TextStyleMark

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

    private fun parseRichParagraph(text: String): RichParagraph {
        return RichParagraph(
            spans = parseInline(text),
        )
    }

    private fun parseInline(text: String): List<TextSpan> {
        val rootStyle = TextStyleMark()
        return parseInlineRecursive(
            text = text,
            startIndex = 0,
            currentStyle = rootStyle,
            stopTag = null,
        ).spans
    }

    private fun parseInlineRecursive(
        text: String,
        startIndex: Int,
        currentStyle: TextStyleMark,
        stopTag: String?,
    ): InlineParseResult {
        val spans = mutableListOf<TextSpan>()
        val buffer = StringBuilder()

        var index = startIndex

        fun flushBuffer() {
            if (buffer.isNotEmpty()) {
                spans += TextSpan(
                    text = buffer.toString(),
                    style = currentStyle,
                )
                buffer.clear()
            }
        }

        while (index < text.length) {
            if (stopTag != null && text.startsWith("</$stopTag>", index)) {
                flushBuffer()
                return InlineParseResult(
                    spans = spans,
                    nextIndex = index + stopTag.length + 3,
                )
            }

            val openTag = findOpeningTag(text, index)
            if (openTag != null) {
                flushBuffer()

                val nestedStyle = when (openTag) {
                    "b" -> currentStyle.copy(bold = true)
                    "i" -> currentStyle.copy(italic = true)
                    "u" -> currentStyle.copy(underline = true)
                    "mark" -> currentStyle.copy(highlighted = true)
                    else -> currentStyle
                }

                val nestedResult = parseInlineRecursive(
                    text = text,
                    startIndex = index + openingTagLength(openTag),
                    currentStyle = nestedStyle,
                    stopTag = openTag,
                )

                spans += nestedResult.spans
                index = nestedResult.nextIndex
                continue
            }

            buffer.append(text[index])
            index++
        }

        flushBuffer()

        return InlineParseResult(
            spans = spans,
            nextIndex = index,
        )
    }

    private fun findOpeningTag(text: String, index: Int): String? {
        return supportedTags.firstOrNull { tag ->
            text.startsWith("<$tag>", index)
        }
    }

    private fun openingTagLength(tag: String): Int = tag.length + 2

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

    private data class InlineParseResult(
        val spans: List<TextSpan>,
        val nextIndex: Int,
    )

    private companion object {
        val frontMatterWeekNumberRegex = Regex("""(?m)^weekNumber:\s*(\d+)\s*$""")
        val frontMatterWeekTitleRegex = Regex("""(?m)^weekTitle:\s*(.+?)\s*$""")
        val englishWeekHeaderRegex = Regex("""(?m)^#\s+Week\s+(\d+)\s+Subject:\s+(.+?)\s*$""")
        val lessonHeaderRegex = Regex("""(?m)^##\s+Lesson\s+(\d+)\s+(.+?)\s*$""")

        val supportedTags = listOf("mark", "b", "i", "u")
    }
}