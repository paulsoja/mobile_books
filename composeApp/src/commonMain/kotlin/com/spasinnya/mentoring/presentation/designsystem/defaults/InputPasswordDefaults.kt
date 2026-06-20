package com.spasinnya.mentoring.presentation.designsystem.defaults

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.spasinnya.mentoring.generated.resources.Res
import com.spasinnya.mentoring.generated.resources.ic_eye_off
import com.spasinnya.mentoring.generated.resources.ic_eye_on
import org.jetbrains.compose.resources.painterResource

interface InputDefaults {
    val placeholder: String
    val singleLine: Boolean
    val shape: Shape
    val visualTransformation: () -> VisualTransformation
    val keyboardType: KeyboardType
    val textStyle: @Composable () -> TextStyle
    val trailingIcon: @Composable () -> Unit
}

@Immutable
data class InputPasswordDefaults(
    val isVisible: Boolean,
    val onToggleVisibility: () -> Unit,
    override val placeholder: String = "********",
    override val singleLine: Boolean = true,
    override val shape: Shape = RoundedCornerShape(12.dp),
    override val keyboardType: KeyboardType = KeyboardType.Password,
    override val textStyle: @Composable () -> TextStyle = {
        MaterialTheme.typography.bodySmall.copy(color = Color(0xFF54595F))
    },
) : InputDefaults {

    override val visualTransformation: () -> VisualTransformation = {
        if (isVisible) VisualTransformation.None
        else PasswordVisualTransformation()
    }

    override val trailingIcon: @Composable () -> Unit = {
        IconButton(
            onClick = onToggleVisibility
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                tint = Color(0xFFB6C3D8),
                contentDescription = "trailingIcon",
                painter = painterResource(
                    if (isVisible) Res.drawable.ic_eye_off else Res.drawable.ic_eye_on
                )
            )
        }
    }
}

@Immutable
data class InputEmailDefaults(
    override val placeholder: String = "email@website.com",
    override val singleLine: Boolean = true,
    override val shape: Shape = RoundedCornerShape(12.dp),
    override val visualTransformation: () -> VisualTransformation = { VisualTransformation.None },
    override val keyboardType: KeyboardType = KeyboardType.Email,
    override val textStyle: @Composable () -> TextStyle = { MaterialTheme.typography.bodySmall.copy(color = Color(0xFF54595F)) },
    override val trailingIcon: @Composable (() -> Unit) = {},
) : InputDefaults

@Immutable
data class InputCommonDefaults(
    override val placeholder: String = "",
    override val singleLine: Boolean = true,
    override val shape: Shape = RoundedCornerShape(12.dp),
    override val visualTransformation: () -> VisualTransformation = { VisualTransformation.None },
    override val keyboardType: KeyboardType = KeyboardType.Text,
    override val textStyle: @Composable () -> TextStyle = { MaterialTheme.typography.bodySmall },
    override val trailingIcon: @Composable (() -> Unit) = {},
) : InputDefaults
