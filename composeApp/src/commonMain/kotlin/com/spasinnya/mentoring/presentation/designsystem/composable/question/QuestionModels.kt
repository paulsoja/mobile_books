package com.spasinnya.mentoring.presentation.designsystem.composable.question

import androidx.compose.runtime.Immutable
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputCommonDefaults
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputDefaults

@Immutable
data class QuestionOption(
    val id: String,
    val label: String,
    val checked: Boolean = false,
    val enabled: Boolean = true,
)

@Immutable
sealed interface QuestionSegment {

    @Immutable
    data class Text(val text: String) : QuestionSegment

    @Immutable
    data class Input(
        val value: String,
        val onValueChange: (String) -> Unit,
        val errorText: String = "",
        val enabled: Boolean = true,
        val inputDefaults: InputDefaults = InputCommonDefaults(),
    ) : QuestionSegment
}
