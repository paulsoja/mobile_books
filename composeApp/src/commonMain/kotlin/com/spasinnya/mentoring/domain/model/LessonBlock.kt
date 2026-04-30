package com.spasinnya.mentoring.domain.model

sealed interface LessonBlock {
    data class Paragraph(val paragraph: RichParagraph) : LessonBlock
    data class CenterText(val paragraph: RichParagraph) : LessonBlock
    data object Divider : LessonBlock
    data class Image(val path: String) : LessonBlock
    data object Table : LessonBlock
}