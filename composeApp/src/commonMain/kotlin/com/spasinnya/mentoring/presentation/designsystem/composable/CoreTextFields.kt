@file:OptIn(ExperimentalMaterial3Api::class)

package com.spasinnya.mentoring.presentation.designsystem.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.ic_check
import books.composeapp.generated.resources.ic_chevron_right
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputCommonDefaults
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputDefaults
import org.jetbrains.compose.resources.painterResource

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

@Composable
fun CoreOutlinedDropDown(
    modifier: Modifier = Modifier.fillMaxWidth(),
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
            modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable),
            value = value,
            onValueChange = {},
            readOnly = true,
            enabled = enabled,
            singleLine = inputDefaults.singleLine,
            shape = inputDefaults.shape,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                focusedTextColor = Color(0xFF54595F),
                unfocusedTextColor = Color(0XFF54595F),
                disabledTextColor = Color(0xFFB6C3D8),
                unfocusedPlaceholderColor = Color(0xFFB6C3D8),
                focusedPlaceholderColor = Color(0xFFB6C3D8),
                disabledPlaceholderColor = Color(0xFFB6C3D8),
                focusedIndicatorColor = Color(0xFFB6C3D8),
                unfocusedIndicatorColor = Color(0xFFB6C3D8),
                disabledIndicatorColor = Color(0xFFB6C3D8),
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
