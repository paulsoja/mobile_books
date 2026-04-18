package com.spasinnya.mentoring.data.parser

internal fun normalizeMarkdown(input: String): String {
    return input
        .replace("\r\n", "\n")
        .replace('\r', '\n')
        .replace("\u000C", "")
        .trim()
}

internal fun cleanupLessonBody(input: String): String {
    return input
        .replace("\r\n", "\n")
        .replace('\r', '\n')
        .replace("￼", "")
        .replace("￾", "")
        .replace(Regex("""[ \t]+\n"""), "\n")
        .replace(Regex("""\n{3,}"""), "\n\n")
        .trim()
}