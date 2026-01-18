package com.spasinnya.mentoring.presentation.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.spasinnya.mentoring.domain.enums.Language

var customAppLanguage by mutableStateOf(Language.System)

/**
 * Platform-specific locale provider used as a workaround for CMP resources.
 * (Until common public API is available.)
 */
expect object LocalAppLocale {
    val current: String @Composable get
    @Composable infix fun provides(value: Language): ProvidedValue<*>
}

/**
 * Use this at the top of your UI tree.
 * When [language] changes, whole subtree is recreated and resources are re-read.
 */
@Composable
fun ProvideAppLocale(
    language: Language,
    content: @Composable () -> Unit
) {
    customAppLanguage = language

    CompositionLocalProvider(
        LocalAppLocale provides customAppLanguage
    ) {
        // key is important to force recomposition for resources
        key(customAppLanguage) {
            content()
        }
    }
}