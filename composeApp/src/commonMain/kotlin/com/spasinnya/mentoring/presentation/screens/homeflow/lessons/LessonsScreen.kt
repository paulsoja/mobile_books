package com.spasinnya.mentoring.presentation.screens.homeflow.lessons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.ic_chevron_right
import books.composeapp.generated.resources.ic_content
import books.composeapp.generated.resources.img_cover_01
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreCardWithContent
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreIconButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTopAppBar
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun LessonsScreen(
    navigateBack: () -> Unit,
    onActionClicked: () -> Unit,
    navigateToWeek: (id: Int) -> Unit
) {

    //val viewModel: LessonsViewModel = viewModel(factory = createLessonsViewModel)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = listOf(Color(0xFFF5F7FC), Color(0xFFE7F2F8))))
            .systemBarsPadding()
            .navigationBarsPadding(),
        containerColor = Color.Transparent,
        topBar = {
            CoreTopAppBar(
                title = "Наставництво - I",
                onBackClick = navigateBack,
                actions = {
                    CoreIconButton(
                        modifier = Modifier.padding(end = 8.dp),
                        iconRes = Res.drawable.ic_content,
                        onClick = onActionClicked
                    )
                }
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
    CoreCardWithContent(
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
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
                CoreTextBody(
                    text = "Вступ"
                )
                CoreTextBody(
                    text = "«Як користуватись книгою»",
                    style = MaterialTheme.typography.bodySmall.copy(
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