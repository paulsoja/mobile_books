package com.spasinnya.mentoring.presentation.designsystem.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CoreCardWithContent(
    modifier: Modifier = Modifier.fillMaxWidth(),
    shape: Shape = RoundedCornerShape(16.dp),
    colors: CardColors = CardDefaults.cardColors(containerColor = Color.White),
    contentPadding: Dp = 16.dp,
    elevation: CardElevation = CardDefaults.cardElevation(),
    borderWidth: Dp = 1.dp,
    borderColor: Color = Color.Unspecified,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {

    val modifier = onClick?.let {
        Modifier.clickable(
            onClick = onClick,
            indication = ripple(bounded = true),
            interactionSource = remember { MutableInteractionSource() }
        ).then(modifier)
    } ?: modifier

    Card(
        modifier = Modifier.clip(shape).then(modifier),
        shape = shape,
        border = BorderStroke(borderWidth, borderColor),
        colors = colors,
        elevation = elevation
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding),
        ) {
            content()
        }
    }
}