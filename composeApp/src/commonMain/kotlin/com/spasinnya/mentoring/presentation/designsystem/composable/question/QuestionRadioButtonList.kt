package com.spasinnya.mentoring.presentation.designsystem.composable.question

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody

@Composable
internal fun QuestionRadioButtonList(
    options: List<QuestionOption>,
    onOptionSelect: (QuestionOption) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        options.forEach { option ->
            QuestionRadioButtonRow(
                option = option,
                onSelect = { onOptionSelect(option) },
            )
        }
    }
}

@Composable
private fun QuestionRadioButtonRow(
    option: QuestionOption,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        RadioButton(
            selected = option.checked,
            onClick = onSelect,
            enabled = option.enabled,
            colors = RadioButtonDefaults.colors(
                selectedColor = Color(0xFF3C4E73),
                unselectedColor = Color(0xFFB6C3D8),
                disabledSelectedColor = Color(0xFFDDE4EF),
                disabledUnselectedColor = Color(0xFFDDE4EF),
            ),
        )
        CoreTextBody(text = option.label)
    }
}
