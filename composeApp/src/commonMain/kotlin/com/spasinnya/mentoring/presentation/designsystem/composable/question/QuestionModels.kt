package com.spasinnya.mentoring.presentation.designsystem.composable.question

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.AnnotatedString
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputCommonDefaults
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputDefaults

@Immutable
data class QuestionOption(
    val id: String,
    val label: AnnotatedString,
    val checked: Boolean = false,
    val enabled: Boolean = true,
)

@Immutable
sealed interface QuestionSegment {

    @Immutable
    data class Text(val text: AnnotatedString) : QuestionSegment

    @Immutable
    data class Input(
        val value: String,
        val onValueChange: (String) -> Unit,
        val errorText: String = "",
        val enabled: Boolean = true,
        val inputDefaults: InputDefaults = InputCommonDefaults(),
    ) : QuestionSegment
}
