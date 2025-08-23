package com.spasinnya.mentoring.presentation.designsystem.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun CoreBadge(
    iconRes: DrawableResource,
    iconTint: Color = Color.White,
    iconSize: Dp = 12.dp,
    backgroundColor: Color = Color.Transparent,
) {
    Badge(
        modifier = Modifier
            .width(28.dp)
            .height(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = Color(0xFFDFA672)),
        containerColor = backgroundColor
    ) {
        Icon(
            imageVector = vectorResource(iconRes),
            contentDescription = "",
            tint = iconTint,
            modifier = Modifier.size(iconSize)
        )
    }
}
