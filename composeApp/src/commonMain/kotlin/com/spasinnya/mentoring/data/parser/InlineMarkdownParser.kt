package com.spasinnya.mentoring.data.parser

import com.spasinnya.mentoring.domain.model.RichParagraph
import com.spasinnya.mentoring.domain.model.TextSpan
import com.spasinnya.mentoring.domain.model.TextStyleMark

/**
 * Parses inline formatting tags — `<b>`, `<i>`, `<u>` and `<mark>`, including
 * nesting — into a list of styled [TextSpan]s.
 *
 * Shared by [MentorshipMarkdownParser] and [HomeworkMarkdownParser] so that both
 * render the same rich-text formatting. Pure and stateless: given the same input
 * it always returns the same spans.
 */
object InlineMarkdownParser {

    fun parseParagraph(text: String): RichParagraph =
        RichParagraph(spans = parse(text))

    fun parse(text: String): List<TextSpan> =
        parseRecursive(
            text = text,
            startIndex = 0,
            currentStyle = TextStyleMark(),
            stopTag = null,
        ).spans

    private fun parseRecursive(
        text: String,
        startIndex: Int,
        currentStyle: TextStyleMark,
        stopTag: String?,
    ): Result {
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
                return Result(
                    spans = spans,
                    nextIndex = index + stopTag.length + 3,
                )
            }

            // Skip orphaned/mismatched closing tags (e.g. a `</b>` whose opening
            // tag was split off by an <input> blank) so they are not rendered.
            val closeTag = findClosingTag(text, index)
            if (closeTag != null) {
                index += closeTag.length + 3
                continue
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

                val nestedResult = parseRecursive(
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

        return Result(
            spans = spans,
            nextIndex = index,
        )
    }

    private fun findOpeningTag(text: String, index: Int): String? =
        supportedTags.firstOrNull { tag ->
            text.startsWith("<$tag>", index)
        }

    private fun findClosingTag(text: String, index: Int): String? =
        supportedTags.firstOrNull { tag ->
            text.startsWith("</$tag>", index)
        }

    private fun openingTagLength(tag: String): Int = tag.length + 2

    private data class Result(
        val spans: List<TextSpan>,
        val nextIndex: Int,
    )

    private val supportedTags = listOf("mark", "b", "i", "u")
}
