@file:OptIn(ExperimentalMaterial3Api::class)

package com.spasinnya.mentoring.presentation.designsystem.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.ic_back
import books.composeapp.generated.resources.ic_logo
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource

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
                            fontSize = 16.sp
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
        actions = { actions?.invoke() },
        colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = Color(0xFFF5F7FC)),
        //elevation = 0.dp
    )
}