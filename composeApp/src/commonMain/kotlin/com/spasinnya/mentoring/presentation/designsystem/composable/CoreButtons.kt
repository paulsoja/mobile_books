package com.spasinnya.mentoring.presentation.designsystem.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun CoreButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    shape: Shape = ButtonDefaults.shape,
    elevation: ButtonElevation? = null,
    border: BorderStroke? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    Button(
        modifier = modifier,
        shape = shape,
        elevation = elevation,
        border = border,
        colors = colors,
        enabled = enabled,
        onClick = onClick) {
            content()
    }
}

@Composable
fun CoreButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String
) {
    Button(modifier = modifier, onClick = onClick) {
        CoreText(text = text)
    }
}

@Composable
fun CorePrimaryButton(
    modifier: Modifier = Modifier.fillMaxWidth().height(68.dp),
    shape: Shape = RoundedCornerShape(40.dp),
    border: BorderStroke = BorderStroke(width = 1.dp, color = Color(0xFF3C4E73)),
    backgroundColor: Color = Color(0xFF3C4E73),
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = backgroundColor,
        contentColor = Color.White
    ),
    iconBefore: DrawableResource? = null,
    text: String,
    iconAfter: DrawableResource? = null,
    iconTint: Color = Color.Unspecified,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    CoreButton(
        modifier = modifier,
        shape = shape,
        elevation = null,
        border = border,
        colors = colors,
        enabled = enabled,
        onClick = onClick,
        content = {
            iconBefore?.let {
                Icon(
                    modifier = Modifier.padding(end = 24.dp),
                    imageVector = vectorResource(it),
                    contentDescription = null,
                    tint = iconTint
                )
            }
            CoreText(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Normal
                )
            )

            iconAfter?.let {
                Icon(
                    modifier = Modifier.padding(start = 24.dp),
                    imageVector = vectorResource(it),
                    contentDescription = null,
                    tint = iconTint
                )
            }
        }
    )
}

@Composable
fun CoreTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    TextButton(modifier = modifier, onClick = onClick) {
        CoreText(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color(0xFF3C4E73),
                textDecoration = TextDecoration.Underline
            )
        )
    }
}

@Composable
fun CoreIconButton(
    modifier: Modifier = Modifier,
    size: Dp = 32.dp,
    iconRes: DrawableResource,
    tint: Color = Color(0xFF3C4E73),
    onClick: () -> Unit
) {
    IconButton(modifier = modifier, onClick = onClick) {
        Icon(
            imageVector = vectorResource(iconRes),
            contentDescription = "",
            tint = tint,
            modifier = Modifier.size(size)
        )
    }
}

@Composable
fun CoreOutlinedButton(
    modifier: Modifier = Modifier.fillMaxWidth().height(68.dp),
    shape: Shape = RoundedCornerShape(40.dp),
    border: BorderStroke = BorderStroke(width = 1.dp, color = Color(0xFF3C4E73)),
    colors: ButtonColors = ButtonDefaults.outlinedButtonColors(
        contentColor = Color(0xFF3C4E73),
        containerColor = Color.Transparent
    ),
    iconBefore: DrawableResource? = null,
    text: String,
    iconAfter: DrawableResource? = null,
    iconTint: Color = Color(0xFF54595F),
    onClick: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier,
        shape = shape,
        elevation = null,
        border = border,
        colors = colors,
        onClick = onClick,
        content = {
            iconBefore?.let {
                Icon(
                    imageVector = vectorResource(it),
                    contentDescription = null,
                    tint = iconTint
                )
            }
            CoreText(
                text = text,
                style = MaterialTheme.typography.titleSmall.copy(
                    color = Color(0xFF54595F),
                    fontWeight = FontWeight.Normal
                )
            )
            Spacer(modifier = Modifier.width(16.dp))

            iconAfter?.let {
                Icon(
                    imageVector = vectorResource(it),
                    contentDescription = null,
                    tint = iconTint
                )
            }
        }
    )
}

