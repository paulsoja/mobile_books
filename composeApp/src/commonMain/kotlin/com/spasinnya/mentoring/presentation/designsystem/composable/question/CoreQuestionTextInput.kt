package com.spasinnya.mentoring.presentation.designsystem.composable.question

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreOutlinedTextField
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalMedium
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody

@Composable
fun CoreQuestionTextInput(
    question: AnnotatedString,
    segments: List<QuestionSegment>,
    modifier: Modifier = Modifier,
    description: AnnotatedString? = null,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        CoreQuestion(question = question, description = description)
        CoreSpacerVerticalMedium()
        segments.forEach { segment ->
            when (segment) {
                is QuestionSegment.Text -> CoreTextBody(text = segment.text)
                is QuestionSegment.Input -> CoreOutlinedTextField(
                    value = segment.value,
                    onValueChange = segment.onValueChange,
                    errorText = segment.errorText,
                    enabled = segment.enabled,
                    inputDefaults = segment.inputDefaults,
                )
            }
        }
    }
}
