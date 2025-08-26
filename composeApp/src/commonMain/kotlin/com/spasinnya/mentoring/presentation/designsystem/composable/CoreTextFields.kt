package com.spasinnya.mentoring.presentation.designsystem.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputCommonDefaults
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputDefaults

@Composable
fun CoreOutlinedTextField(
    modifier: Modifier = Modifier.fillMaxWidth(),
    value: String,
    onValueChange: (String) -> Unit,
    errorText: String = "",
    inputDefaults: InputDefaults = InputCommonDefaults(),
) {
    OutlinedTextField(
        supportingText = {
            errorText.takeIf { it.isNotEmpty() }?.let {
                CoreTextBody(text = it, style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFED1A3D)))
            }
        },
        isError = errorText.isNotEmpty(),
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        singleLine = inputDefaults.singleLine,
        shape = inputDefaults.shape,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = inputDefaults.keyboardType),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            focusedTextColor = Color(0xFF54595F),
            unfocusedTextColor = Color(0XFF54595F),
            unfocusedPlaceholderColor = Color(0xFFB6C3D8),
            focusedPlaceholderColor = Color(0xFFB6C3D8),
            errorSupportingTextColor = Color(0xFFED1A3D),
            errorIndicatorColor = Color(0xFFED1A3D),
            focusedIndicatorColor = Color(0xFFB6C3D8),
            unfocusedIndicatorColor = Color(0xFFB6C3D8),
            disabledIndicatorColor = Color(0xFFB6C3D8),
        ),
        placeholder = {
            inputDefaults.placeholder.takeIf { it.isNotEmpty() }?.let {
                CoreTextBody(text = it, style = MaterialTheme.typography.bodySmall)
            }
        },
        trailingIcon = { inputDefaults.trailingIcon.invoke() },
        textStyle = inputDefaults.textStyle.invoke(),
    )
}
