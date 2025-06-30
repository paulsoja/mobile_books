package com.spasinnya.mentoring.presentation.homeflow.lessons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.ic_back
import books.composeapp.generated.resources.ic_chevron_right
import books.composeapp.generated.resources.ic_content
import books.composeapp.generated.resources.img_cover_01
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun LessonsScreen(
    navigateBack: () -> Unit,
    onActionClicked: () -> Unit,
    navigateToWeek: (id: Int) -> Unit
) {

    val viewModel: LessonsViewModel = viewModel(factory = LessonsViewModel.Factory)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = listOf(Color(0xFFF5F7FC), Color(0xFFE7F2F8))))
            .systemBarsPadding()
            .navigationBarsPadding(),
        backgroundColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Наставництво - I",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF3C4E73),
                            fontSize = 16.sp
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.padding(start = 8.dp),
                        onClick = navigateBack
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.ic_back),
                            contentDescription = "",
                            tint = Color(0xFF3C4E73),
                            modifier = Modifier.size(32.dp).padding(all = 2.dp)
                        )
                    }
                },
                actions = {
                    IconButton(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = onActionClicked
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.ic_content),
                            contentDescription = "",
                            tint = Color(0xFF3C4E73),
                            modifier = Modifier.size(32.dp).padding(all = 2.dp)
                        )
                    }
                },
                backgroundColor = Color(0xFFF5F7FC),
                elevation = 0.dp
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    modifier = Modifier.fillMaxWidth().height(100.dp).clip(shape = RoundedCornerShape(16.dp)),
                    painter = painterResource(Res.drawable.img_cover_01),
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth
                )
                Spacer(modifier = Modifier.height(8.dp))
                LessonItem { navigateToWeek.invoke(1) }
                LessonItem { navigateToWeek.invoke(1) }
                LessonItem { navigateToWeek.invoke(1) }
                LessonItem { navigateToWeek.invoke(1) }
                LessonItem { navigateToWeek.invoke(1) }
                LessonItem { navigateToWeek.invoke(1) }
                LessonItem { navigateToWeek.invoke(1) }
                LessonItem { navigateToWeek.invoke(1) }
                LessonItem { navigateToWeek.invoke(1) }
                LessonItem { navigateToWeek.invoke(1) }
                LessonItem { navigateToWeek.invoke(1) }
                LessonItem { navigateToWeek.invoke(1) }
            }
        }
    )
}

@Composable
fun LessonItem(
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                onClick = onClick,
                indication = ripple(bounded = true),
                interactionSource = remember { MutableInteractionSource() }
            ),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.White,
        elevation = 1.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Вступ",
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFFDFA672),
                        fontSize = 14.sp
                    )
                )
                Text(
                    text = "«Як користуватись книгою»",
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3C4E73),
                        fontSize = 14.sp
                    )
                )
            }
            Icon(
                imageVector = vectorResource(Res.drawable.ic_chevron_right),
                contentDescription = "",
                tint = Color.Unspecified,
                modifier = Modifier.size(32.dp).padding(all = 2.dp)
            )
        }
    }
}