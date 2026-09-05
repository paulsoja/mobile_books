package com.spasinnya.mentoring.presentation.designsystem.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spasinnya.mentoring.presentation.designsystem.Secondary300
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

@Composable
fun CoreTextBadge(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Secondary300,
    textColor: Color = Color.White,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        CoreText(
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelMedium.copy(
                color = textColor,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )
        )
    }
}
