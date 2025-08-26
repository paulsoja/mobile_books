package com.spasinnya.mentoring.presentation.designsystem.defaults

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.ic_eye_off
import books.composeapp.generated.resources.ic_eye_on
import org.jetbrains.compose.resources.painterResource

interface InputDefaults {
    val placeholder: String
    val singleLine: Boolean
    val shape: Shape
    val visualTransformation: VisualTransformation
    val keyboardType: KeyboardType
    val textStyle: @Composable () -> TextStyle
    val trailingIcon: @Composable () -> Unit
}

data class InputPasswordDefaults(
    val showPassword: () -> Boolean = { false },
    override val placeholder: String = "********",
    override val singleLine: Boolean = true,
    override val shape: Shape = RoundedCornerShape(12.dp),
    override val visualTransformation: VisualTransformation = if (showPassword.invoke()) VisualTransformation.None else PasswordVisualTransformation(),
    override val keyboardType: KeyboardType = KeyboardType.Password,
    override val textStyle: @Composable () -> TextStyle = { MaterialTheme.typography.bodySmall.copy(color = Color(0xFF54595F)) },
    override val trailingIcon: @Composable () -> Unit = {
        IconButton(onClick = { showPassword.invoke() }) {
            Icon(
                modifier = Modifier.size(24.dp),
                tint = Color(0xFFB6C3D8),
                contentDescription = "trailingIcon",
                painter = painterResource(if (showPassword()) Res.drawable.ic_eye_off else Res.drawable.ic_eye_on)
            )
        }
    },
) : InputDefaults

data class InputEmailDefaults(
    override val placeholder: String = "email@website.com",
    override val singleLine: Boolean = true,
    override val shape: Shape = RoundedCornerShape(12.dp),
    override val visualTransformation: VisualTransformation = VisualTransformation.None,
    override val keyboardType: KeyboardType = KeyboardType.Email,
    override val textStyle: @Composable () -> TextStyle = { MaterialTheme.typography.bodySmall.copy(color = Color(0xFF54595F)) },
    override val trailingIcon: @Composable (() -> Unit) = {},
) : InputDefaults

data class InputCommonDefaults(
    override val placeholder: String = "",
    override val singleLine: Boolean = true,
    override val shape: Shape = RoundedCornerShape(12.dp),
    override val visualTransformation: VisualTransformation = VisualTransformation.None,
    override val keyboardType: KeyboardType = KeyboardType.Text,
    override val textStyle: @Composable () -> TextStyle = { MaterialTheme.typography.bodySmall },
    override val trailingIcon: @Composable (() -> Unit) = {},
) : InputDefaults
