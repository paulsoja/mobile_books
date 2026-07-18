package com.spasinnya.mentoring.presentation.designsystem.composable.question

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalMedium
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalSmall
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody

/**
 * A single row of a [CoreQuestionTextRadioButton]: a short statement with its
 * own set of single-choice radio options.
 */
@Immutable
data class QuestionRadioRow(
    val id: String,
    val prompt: AnnotatedString,
    val options: List<QuestionOption>,
)

/**
 * A question header followed by several rows, each of which is a statement with
 * its own single-choice radio group. Backs `Type: text_radiobutton`.
 */
@Composable
fun CoreQuestionTextRadioButton(
    question: AnnotatedString,
    rows: List<QuestionRadioRow>,
    onOptionSelect: (rowId: String, option: QuestionOption) -> Unit,
    modifier: Modifier = Modifier,
    description: AnnotatedString? = null,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        CoreQuestion(question = question, description = description)
        CoreSpacerVerticalMedium()
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            rows.forEach { row ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    CoreTextBody(text = row.prompt)
                    CoreSpacerVerticalSmall()
                    QuestionRadioButtonList(
                        options = row.options,
                        onOptionSelect = { option -> onOptionSelect(row.id, option) },
                    )
                }
            }
        }
    }
}
