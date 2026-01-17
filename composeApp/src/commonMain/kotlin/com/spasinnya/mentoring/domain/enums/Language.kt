package com.spasinnya.mentoring.domain.enums

enum class Language(val tag: String) {
    UA("uk"),
    EN("en"),
    RU("ru"),
    DE("de"),
    System("en");

    companion object {
        val default: Language = System

        fun fromTag(tag: String?, default: Language = System): Language {
            if (tag.isNullOrBlank()) return default

            val normalized = tag.trim().lowercase()

            val base = normalized
                .replace('_', '-')
                .substringBefore('-')

            return entries.firstOrNull { it.tag == base } ?: default
        }
    }
}