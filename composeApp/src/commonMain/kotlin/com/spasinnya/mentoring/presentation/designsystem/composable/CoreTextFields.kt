package com.spasinnya.mentoring.presentation.designsystem.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CoreOutlinedTextField(
    modifier: Modifier = Modifier.fillMaxWidth(),
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    shape: Shape = RoundedCornerShape(12.dp),
    placeholder: String? = null,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall,
    label: String? = null
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        singleLine = singleLine,
        shape = shape,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            unfocusedPlaceholderColor = Color(0xFFB6C3D8),
            focusedPlaceholderColor = Color(0xFFB6C3D8),
        ),
        placeholder = {
            placeholder?.let {
                CoreTextBody(text = it)
            }

        },
        textStyle = textStyle,
        label = {
            label?.let {
                CoreTextSubtitle(
                    text = label,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    )
}
