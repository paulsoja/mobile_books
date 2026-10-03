package com.spasinnya.mentoring.presentation.designsystem.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.spasinnya.mentoring.generated.resources.Res
import com.spasinnya.mentoring.generated.resources.ic_check
import com.spasinnya.mentoring.generated.resources.ic_chevron_right
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputCommonDefaults
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputDefaults
import org.jetbrains.compose.resources.painterResource

@Composable
fun CoreOutlinedTextField(
    modifier: Modifier = Modifier.fillMaxWidth(),
    value: String,
    onValueChange: (String) -> Unit,
    errorText: String = "",
    enabled: Boolean = true,
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
        enabled = enabled,
        singleLine = inputDefaults.singleLine,
        shape = inputDefaults.shape,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = inputDefaults.keyboardType),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color(0xFFE7EBF4),
            errorContainerColor = Color.Transparent,
            focusedTextColor = Color(0xFF54595F),
            unfocusedTextColor = Color(0XFF54595F),
            disabledTextColor = Color(0xFFB6C3D8),
            unfocusedPlaceholderColor = Color(0xFFB6C3D8),
            focusedPlaceholderColor = Color(0xFFB6C3D8),
            disabledPlaceholderColor = Color(0xFFB6C3D8),
            errorSupportingTextColor = Color(0xFFED1A3D),
            errorIndicatorColor = Color(0xFFED1A3D),
            focusedIndicatorColor = Color(0xFFB6C3D8),
            unfocusedIndicatorColor = Color(0xFFB6C3D8),
            disabledIndicatorColor = Color(0xFFDDE4EF),
        ),
        visualTransformation = inputDefaults.visualTransformation(),
        placeholder = {
            inputDefaults.placeholder.takeIf { it.isNotEmpty() }?.let {
                CoreTextBody(text = it, style = MaterialTheme.typography.bodySmall)
            }
        },
        trailingIcon = {
            inputDefaults.trailingIcon.invoke()
                       },
        textStyle = inputDefaults.textStyle.invoke(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoreOutlinedDropDown(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    options: List<String>,
    enabled: Boolean = true,
    inputDefaults: InputDefaults = InputCommonDefaults(),
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded, onExpandedChange = { if (enabled) expanded = it }, modifier = modifier
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable),
            value = value,
            onValueChange = {},
            readOnly = true,
            enabled = enabled,
            singleLine = inputDefaults.singleLine,
            shape = inputDefaults.shape,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color(0xFFE7EBF4),
                errorContainerColor = Color.Transparent,
                focusedTextColor = Color(0xFF54595F),
                unfocusedTextColor = Color(0XFF54595F),
                disabledTextColor = Color(0xFFB6C3D8),
                unfocusedPlaceholderColor = Color(0xFFB6C3D8),
                focusedPlaceholderColor = Color(0xFFB6C3D8),
                disabledPlaceholderColor = Color(0xFFB6C3D8),
            ),
            placeholder = {
                inputDefaults.placeholder.takeIf { it.isNotEmpty() }?.let {
                    CoreTextBody(text = it, style = MaterialTheme.typography.bodySmall)
                }
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier.size(20.dp).rotate(if (expanded) 270f else 90f),
                    painter = painterResource(Res.drawable.ic_chevron_right),
                    contentDescription = null,
                    tint = Color(0xFFB6C3D8)
                )
            },
            textStyle = inputDefaults.textStyle.invoke(),
        )

        ExposedDropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false }, containerColor = Color.White
        ) {
            options.forEach { option ->
                Box(
                    modifier = Modifier.fillMaxWidth()
                        .background(color = if (option == value) Color(0xFFF5F7FC) else MaterialTheme.colorScheme.background)
                ) {
                    DropdownMenuItem(text = {
                        CoreTextBody(
                            text = option, style = MaterialTheme.typography.bodySmall
                        )
                    }, onClick = {
                        onValueChange(option)
                        expanded = false
                    }, trailingIcon = {
                        if (option == value) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                painter = painterResource(Res.drawable.ic_check),
                                contentDescription = null,
                                tint = Color(0xFF608CB9)
                            )
                        }
                    })
                }
            }
        }
    }
}

@Composable
fun CoreOutlinedTextField(
    modifier: Modifier = Modifier.fillMaxWidth(),
    value: String,
    onValueChange: (String) -> Unit,
    errorText: String = "",
    enabled: Boolean = true,
    inputDefaults: InputDefaults = InputCommonDefaults(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isError = errorText.isNotEmpty()
    val visualTransformation = inputDefaults.visualTransformation()

    val textColor = when {
        !enabled -> Color(0xFFB6C3D8)
        else -> Color(0xFF54595F)
    }
    val cursorColor = when {
        isError -> Color(0xFFED1A3D)
        else -> MaterialTheme.colorScheme.primary
    }

    val colors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color(0xFFE7EBF4),
        errorContainerColor = Color.Transparent,
        focusedTextColor = Color(0xFF54595F),
        unfocusedTextColor = Color(0xFF54595F),
        disabledTextColor = Color(0xFFB6C3D8),
        errorTextColor = Color(0xFF54595F),
        unfocusedPlaceholderColor = Color(0xFFB6C3D8),
        focusedPlaceholderColor = Color(0xFFB6C3D8),
        disabledPlaceholderColor = Color(0xFFB6C3D8),
        errorSupportingTextColor = Color(0xFFED1A3D),
        errorBorderColor = Color(0xFFED1A3D),
        focusedBorderColor = Color(0xFFB6C3D8),
        unfocusedBorderColor = Color(0xFFB6C3D8),
        disabledBorderColor = Color(0xFFDDE4EF),
    )

    val placeholder: (@Composable () -> Unit)? =
        if (inputDefaults.placeholder.isNotEmpty()) {
            {
                CoreTextBody(
                    text = inputDefaults.placeholder,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        } else {
            null
        }

    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        singleLine = inputDefaults.singleLine,
        textStyle = inputDefaults.textStyle.invoke().copy(
            color = textColor,
            textAlign = TextAlign.Center,
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = inputDefaults.keyboardType,
        ),
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(cursorColor),
        decorationBox = { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value = value,
                innerTextField = innerTextField,
                enabled = enabled,
                singleLine = true,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                isError = false,
                placeholder = placeholder,
                supportingText = null,
                trailingIcon = null,
                colors = colors,
                contentPadding = contentPadding,
                container = {
                    OutlinedTextFieldDefaults.Container(
                        enabled = enabled,
                        isError = isError,
                        interactionSource = interactionSource,
                        colors = colors,
                        shape = inputDefaults.shape,
                    )
                },
            )
        },
    )
}