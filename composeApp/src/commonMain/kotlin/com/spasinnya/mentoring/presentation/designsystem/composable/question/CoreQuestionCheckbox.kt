package com.spasinnya.mentoring.presentation.designsystem.composable.question

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalMedium

/**
 * Type 1 — a question with a list of checkboxes rendered below it.
 */
@Composable
fun CoreQuestionCheckbox(
    question: AnnotatedString,
    options: List<QuestionOption>,
    onOptionToggle: (QuestionOption) -> Unit,
    modifier: Modifier = Modifier,
    description: AnnotatedString? = null,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        CoreQuestion(question = question, description = description)
        CoreSpacerVerticalMedium()
        QuestionCheckboxList(
            options = options,
            onOptionToggle = onOptionToggle,
        )
    }
}
