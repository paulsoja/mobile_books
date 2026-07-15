package com.spasinnya.mentoring.presentation.designsystem.composable.question

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreOutlinedTextField
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalMedium
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputCommonDefaults
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputDefaults

@Composable
fun CoreQuestionCheckboxInput(
    question: AnnotatedString,
    options: List<QuestionOption>,
    onOptionToggle: (QuestionOption) -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    description: AnnotatedString? = null,
    errorText: String = "",
    enabled: Boolean = true,
    inputDefaults: InputDefaults = InputCommonDefaults(),
) {
    Column(modifier = modifier.fillMaxWidth()) {
        CoreQuestion(question = question, description = description)
        CoreSpacerVerticalMedium()
        QuestionCheckboxList(
            options = options,
            onOptionToggle = onOptionToggle,
        )
        CoreSpacerVerticalMedium()
        CoreOutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            errorText = errorText,
            enabled = enabled,
            inputDefaults = inputDefaults,
        )
    }
}
