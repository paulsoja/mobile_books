package com.spasinnya.mentoring.presentation.designsystem.composable.question

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer

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
        CoreQuestion(
            question = question,
            description = description,
        )
        CoreSpacerVerticalMedium()

        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth(),
        ) {
            val minInputWidth = maxWidth * 0.3f
            val maxInputWidth = maxWidth

            val textMeasurer = rememberTextMeasurer()
            val density = LocalDensity.current
            val inputTextStyle = MaterialTheme.typography.bodyLarge
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                segments.forEach { segment ->
                    when (segment) {
                        is QuestionSegment.Text -> {
                            segment.text.toFlowWords().forEach { word ->
                                CoreTextBody(
                                    modifier = Modifier.align(
                                        Alignment.CenterVertically,
                                    ),
                                    text = word,
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            }
                        }

                        is QuestionSegment.Input -> {
                            val textWidth = with(density) {
                                textMeasurer.measure(
                                    text = AnnotatedString(segment.value),
                                    style = inputTextStyle,
                                    softWrap = false,
                                    maxLines = 1,
                                ).size.width.toDp()
                            }

                            val inputWidth = (textWidth + 40.dp)
                                .coerceIn(minInputWidth, maxInputWidth)

                            CoreOutlinedTextField(
                                modifier = Modifier
                                    .width(inputWidth)
                                    .height(32.dp)
                                    .padding(horizontal = 1.dp)
                                    .align(Alignment.CenterVertically),
                                value = segment.value,
                                onValueChange = segment.onValueChange,
                                errorText = segment.errorText,
                                enabled = segment.enabled,
                                inputDefaults = segment.inputDefaults,
                                contentPadding = PaddingValues(horizontal = 1.dp)
                            )
                        }

                        is QuestionSegment.NumberInput -> {
                            CoreSquareNumberField(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .align(Alignment.CenterVertically),
                                value = segment.value,
                                onValueChange = segment.onValueChange,
                                enabled = segment.enabled,
                            )
                        }
                    }
                }
            }
        }
    }
}

private val FlowWordRegex = Regex("""\S+\s*""")

private fun AnnotatedString.toFlowWords(): List<AnnotatedString> =
    FlowWordRegex.findAll(text).map { match ->
        subSequence(
            startIndex = match.range.first,
            endIndex = match.range.last + 1,
        )
    }.toList()

@Composable
private fun CoreQuestionNumberRow(
    input: QuestionSegment.NumberInput,
    text: QuestionSegment.Text?,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        CoreSquareNumberField(
            value = input.value,
            onValueChange = input.onValueChange,
            enabled = input.enabled,
        )

        text?.let {
            CoreTextBody(
                text = it.text,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}

private sealed interface QuestionRow {
    data class Number(
        val input: QuestionSegment.NumberInput,
        val text: QuestionSegment.Text?,
    ) : QuestionRow

    data class Text(
        val segment: QuestionSegment.Text,
    ) : QuestionRow

    data class Input(
        val segment: QuestionSegment.Input,
    ) : QuestionRow
}

private fun List<QuestionSegment>.toQuestionRows(): List<QuestionRow> =
    buildList {
        this@toQuestionRows.forEach { segment ->
            when (segment) {
                is QuestionSegment.NumberInput -> {
                    add(
                        QuestionRow.Number(
                            input = segment,
                            text = null,
                        ),
                    )
                }

                is QuestionSegment.Text -> {
                    val previousRow = lastOrNull()

                    if (previousRow is QuestionRow.Number && previousRow.text == null) {
                        set(lastIndex, previousRow.copy(text = segment))
                    } else {
                        add(QuestionRow.Text(segment))
                    }
                }

                is QuestionSegment.Input -> {
                    add(QuestionRow.Input(segment))
                }
            }
        }
    }