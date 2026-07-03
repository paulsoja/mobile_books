@file:OptIn(ExperimentalMaterial3Api::class)

package com.spasinnya.mentoring.presentation.designsystem.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spasinnya.mentoring.generated.resources.Res
import com.spasinnya.mentoring.generated.resources.ic_back
import com.spasinnya.mentoring.generated.resources.ic_close
import com.spasinnya.mentoring.generated.resources.ic_logo
import com.spasinnya.mentoring.presentation.designsystem.UiDimensions
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource

private val NavigationIconBalanceWidth = 56.dp

@Composable
fun CoreTopBar(
    actionIcon: DrawableResource,
    showLogo: Boolean = true,
    onActionClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .padding(end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CoreIconButton(
            iconRes = actionIcon,
            onClick = onActionClicked
        )

        CoreSpacerHorizontalWeight()
        if (showLogo) {
            Icon(
                imageVector = vectorResource(Res.drawable.ic_logo),
                contentDescription = "",
                tint = Color.Unspecified,
                modifier = Modifier.height(40.dp)
            )
        }
    }
}

@Composable
fun CoreTopAppBar(
    title: String? = null,
    subtitle: String? = null,
    actions: @Composable (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null,
) {
    TopAppBar(
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                title?.let {
                    CoreTextBody(
                        text = title,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF3C4E73),
                            fontSize = 14.sp
                        )
                    )
                }

                subtitle?.let {
                    CoreTextBody(
                        text = subtitle,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF3C4E73),
                            fontSize = 16.sp
                        )
                    )
                }
            }

        },
        navigationIcon = {
            onBackClick?.let {
                CoreIconButton(
                    modifier = Modifier.padding(start = 8.dp),
                    iconRes = Res.drawable.ic_back,
                    onClick = onBackClick
                )
            }
        },
        actions = {
            when {
                actions != null -> actions.invoke()
                onBackClick != null -> Spacer(Modifier.width(NavigationIconBalanceWidth))
            }
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = Color(0xFFF5F7FC)),
    )
}

@Composable
fun CoreModalTopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    onCloseClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth().height(UiDimensions.modalTopBarHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            color = Color(0xFF3C4E73),
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(Modifier.width(4.dp))
        CoreIconButton(size = 32.dp, iconRes = Res.drawable.ic_close, tint = Color(0xFFB6C3D8), onClick = onCloseClick)
    }
}