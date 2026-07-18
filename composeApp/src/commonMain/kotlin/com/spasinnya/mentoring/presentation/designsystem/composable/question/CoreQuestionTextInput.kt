package com.spasinnya.mentoring.presentation.designsystem.composable.question

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreOutlinedTextField
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalMedium
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSquareNumberField
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody
import androidx.compose.material3.MaterialTheme

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

        var index = 0
        while (index < segments.size) {
            when (val segment = segments[index]) {
                is QuestionSegment.NumberInput -> {
                    // Pair the square number field with the text that follows it.
                    val trailing = segments.getOrNull(index + 1) as? QuestionSegment.Text
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        CoreSquareNumberField(
                            value = segment.value,
                            onValueChange = segment.onValueChange,
                            enabled = segment.enabled,
                        )
                        trailing?.let {
                            CoreTextBody(
                                text = it.text,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }
                    index += if (trailing != null) 2 else 1
                }

                is QuestionSegment.Text -> {
                    CoreTextBody(text = segment.text)
                    index++
                }

                is QuestionSegment.Input -> {
                    CoreOutlinedTextField(
                        value = segment.value,
                        onValueChange = segment.onValueChange,
                        errorText = segment.errorText,
                        enabled = segment.enabled,
                        inputDefaults = segment.inputDefaults,
                    )
                    index++
                }
            }
        }
    }
}
