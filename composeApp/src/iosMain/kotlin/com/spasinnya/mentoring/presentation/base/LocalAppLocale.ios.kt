package com.spasinnya.mentoring.presentation.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.staticCompositionLocalOf
import com.spasinnya.mentoring.domain.enums.Language
import platform.Foundation.NSLocale
import platform.Foundation.NSUserDefaults
import platform.Foundation.preferredLanguages

actual object LocalAppLocale {
    private const val LANG_KEY = "AppleLanguages"

    private val systemDefaultTag: String =
        (NSLocale.preferredLanguages.firstOrNull() as? String) ?: "en"

    private val LocalLocale = staticCompositionLocalOf { systemDefaultTag.substringBefore("-") }

    actual val current: String
        @Composable get() = LocalLocale.current

    @Composable
    actual infix fun provides(value: Language): ProvidedValue<*> {
        val newTag = when (value) {
            Language.System -> systemDefaultTag
            else -> value.tag
        }

        // iOS uses AppleLanguages to override language
        if (value == Language.System) {
            NSUserDefaults.standardUserDefaults.removeObjectForKey(LANG_KEY)
        } else {
            NSUserDefaults.standardUserDefaults.setObject(listOf(newTag), LANG_KEY)
        }

        return LocalLocale.provides(newTag.substringBefore("-"))
    }
}