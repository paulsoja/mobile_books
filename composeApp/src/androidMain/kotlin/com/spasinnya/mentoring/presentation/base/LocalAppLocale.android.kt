@file:OptIn(ExperimentalComposeUiApi::class)

package com.spasinnya.mentoring.presentation.base

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalResources
import com.spasinnya.mentoring.domain.enums.Language
import java.util.Locale

actual object LocalAppLocale {

    private var systemDefault: Locale? = null

    actual val current: String
        @Composable get() =
            Locale.getDefault().toLanguageTag().substringBefore("-") // "uk"/"en"/...

    @Composable
    actual infix fun provides(value: Language): ProvidedValue<*> {
        val baseConfig = LocalConfiguration.current
        val newConfig = Configuration(baseConfig)

        if (systemDefault == null) systemDefault = Locale.getDefault()

        val newLocale: Locale = when (value) {
            Language.System -> systemDefault!!
            else -> Locale.forLanguageTag(value.tag) // uk/en/ru/de
        }

        // Make it default for JVM
        Locale.setDefault(newLocale)

        // Update configuration that Compose observes
        newConfig.setLocale(newLocale)
        newConfig.setLayoutDirection(newLocale)

        // Update Android resources too (helps platform lookups, dates, etc.)
        val resources = LocalResources.current
        @Suppress("DEPRECATION")
        resources.updateConfiguration(newConfig, resources.displayMetrics)

        // Provide new configuration to Compose
        return LocalConfiguration.provides(newConfig)
    }
}