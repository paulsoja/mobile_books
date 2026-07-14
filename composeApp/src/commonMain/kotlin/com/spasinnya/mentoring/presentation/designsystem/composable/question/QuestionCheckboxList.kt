package com.spasinnya.mentoring.presentation.designsystem.composable.question

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody

@Composable
internal fun QuestionCheckboxList(
    options: List<QuestionOption>,
    onOptionToggle: (QuestionOption) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        options.forEach { option ->
            QuestionCheckboxRow(
                option = option,
                onToggle = { onOptionToggle(option.copy(checked = !option.checked)) },
            )
        }
    }
}

@Composable
private fun QuestionCheckboxRow(
    option: QuestionOption,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Checkbox(
            checked = option.checked,
            onCheckedChange = { onToggle() },
            enabled = option.enabled,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF3C4E73),
                uncheckedColor = Color(0xFFB6C3D8),
                checkmarkColor = Color.White,
                disabledCheckedColor = Color(0xFFDDE4EF),
                disabledUncheckedColor = Color(0xFFDDE4EF),
            ),
        )
        CoreTextBody(text = option.label)
    }
}
