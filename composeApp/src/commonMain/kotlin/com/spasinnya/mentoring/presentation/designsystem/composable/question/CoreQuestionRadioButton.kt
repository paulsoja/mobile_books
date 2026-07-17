package com.spasinnya.mentoring.presentation.designsystem.composable.question

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalMedium

/**
 * A question with a list of radio buttons rendered below it — single choice.
 */
@Composable
fun CoreQuestionRadioButton(
    question: AnnotatedString,
    options: List<QuestionOption>,
    onOptionSelect: (QuestionOption) -> Unit,
    modifier: Modifier = Modifier,
    description: AnnotatedString? = null,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        CoreQuestion(question = question, description = description)
        CoreSpacerVerticalMedium()
        QuestionRadioButtonList(
            options = options,
            onOptionSelect = onOptionSelect,
        )
    }
}
