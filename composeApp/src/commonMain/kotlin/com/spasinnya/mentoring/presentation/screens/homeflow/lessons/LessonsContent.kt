package com.spasinnya.mentoring.presentation.screens.homeflow.lessons

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spasinnya.mentoring.domain.mapper.asAnnotatedString
import com.spasinnya.mentoring.domain.model.LessonBlock
import com.spasinnya.mentoring.generated.resources.Res
import com.spasinnya.mentoring.generated.resources.ic_check
import com.spasinnya.mentoring.presentation.designsystem.AppResources
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreBadge
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreText
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTopAppBar
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable
fun LessonsContent(
    state: LessonsContract.State,
    navigateBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF5F7FC))
            .statusBarsPadding()
    ) {
        CoreTopAppBar(
            title = "Тиждень ${state.weekNumber}",
            subtitle = state.weekTitle,
            onBackClick = navigateBack
        )

        val scope = rememberCoroutineScope()

        val pagerState = rememberPagerState(
            initialPage = 0,
            pageCount = { state.lessons.size }
        )

        Spacer(modifier = Modifier.height(8.dp))

        CompositionLocalProvider(LocalRippleConfiguration provides null) {
            PrimaryTabRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(78.dp)
                    .padding(horizontal = 8.dp),
                selectedTabIndex = pagerState.currentPage,
                containerColor = Color(0xFFF5F7FC),
                indicator = {},
                divider = {},
                contentColor = Color(0xFF3C4E73),
                tabs = {
                    val badgeSize = 22.dp
                    val badgeOverlap = badgeSize / 2

                    state.lessons.forEachIndexed { index, _ ->
                        val isSelected = pagerState.currentPage == index
                        val isCompleted = (index == 0 || index == 1)

                        Tab(
                            modifier = Modifier
                                .padding(horizontal = 2.dp)
                                .padding(top = 8.dp)
                                .tabBackground(
                                    color = if (isSelected) Color.White else Color(0xFFE7F2F8),
                                    topPadding = badgeOverlap,
                                    radius = 16.dp
                                ),
                            selected = isSelected,
                            onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                            text = {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                                            .padding(bottom = 8.dp),
                                        verticalArrangement = Arrangement.Bottom,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        CoreTextBody(
                                            text = "Урок",
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontWeight = FontWeight.Normal,
                                                color = if (isSelected) Color(0xFFDFA672) else Color(0xFF96A4BA),
                                                fontSize = 12.sp,
                                                lineHeight = 18.sp
                                            )
                                        )
                                        CoreTextBody(
                                            text = "${index + 1}",
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = Color(0xFF3C4E73),
                                                fontSize = 20.sp,
                                                lineHeight = 16.sp
                                            )
                                        )
                                    }

                                    if (isCompleted) {
                                        Box(
                                            modifier = Modifier
                                                .align(Alignment.TopCenter)
                                                .padding(top = 2.dp)
                                        ) {
                                            CoreBadge(
                                                iconRes = Res.drawable.ic_check,
                                                backgroundColor = if (isSelected) Color(0xFFDFA672) else Color(0xFFDFA672)
                                            )
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            )
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .background(color = Color.White),
            contentPadding = PaddingValues(horizontal = 0.dp),
            pageSpacing = 8.dp
        ) { page ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                CoreTextBody(
                    text = state.lessons[page].title,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3C4E73),
                        fontSize = 20.sp
                    )
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(color = Color(0xFFFAF3EA))
                        .padding(all = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CoreTextBody(
                            text = "\uD83D\uDCD6",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 32.sp
                            )
                        )

                        state.lessons[page].quotes.getOrNull(0)?.let { quote ->
                            CoreTextBody(
                                text = quote.asAnnotatedString(
                                    highlightColor = Color.Green
                                ),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFCF7D47),
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp
                                )
                            )
                        }
                    }

                    state.lessons[page].quotes.getOrNull(1)?.let { quote ->
                        CoreTextBody(
                            text = quote.asAnnotatedString(
                                highlightColor = Color.Green
                            ),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFFCF7D47),
                                fontSize = 18.sp,
                                lineHeight = 28.sp
                            )
                        )
                    }
                }

                state.lessons[page].blocks.forEach { block ->
                    when (block) {
                        is LessonBlock.Paragraph -> {
                            CoreText(
                                text = block.paragraph.asAnnotatedString(
                                    highlightColor = Color.Green
                                ),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Normal,
                                    color = Color(0xFF54595F),
                                    fontSize = 18.sp,
                                    lineHeight = 28.sp
                                ),
                            )
                        }

                        is LessonBlock.Divider -> {
                            HorizontalDivider(
                                modifier = Modifier.padding(vertical = 16.dp)
                            )
                        }

                        is LessonBlock.Image -> {
                            Image(
                                modifier = Modifier.fillMaxWidth(),
                                contentScale = ContentScale.Fit,
                                contentDescription = null,
                                painter = painterResource(AppResources.drawable(block.path)),
                            )
                        }

                        is LessonBlock.Table -> {
                            CoreText(
                                text = "table",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().height(100.dp).background(Color.Green).padding(top = 12.dp),
                            )
                        }

                        is LessonBlock.CenterText -> {
                            CoreText(
                                text = block.paragraph.asAnnotatedString(
                                    highlightColor = Color.Green
                                ),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Normal,
                                    color = Color(0xFF54595F),
                                    fontSize = 18.sp,
                                    lineHeight = 28.sp
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }
                }
            }
        }
    }
}

fun Modifier.tabBackground(
    color: Color,
    topPadding: Dp,
    radius: Dp
) = this.drawWithCache {
    val topPaddingPx = topPadding.toPx()
    val radiusPx = radius.toPx()

    val left = 0f
    val top = topPaddingPx
    val right = size.width
    val bottom = size.height

    val path = Path().apply {
        moveTo(left, top + radiusPx)
        arcTo(
            rect = Rect(left, top, left + 2 * radiusPx, top + 2 * radiusPx),
            startAngleDegrees = 180f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )
        lineTo(right - radiusPx, top)
        arcTo(
            rect = Rect(right - 2 * radiusPx, top, right, top + 2 * radiusPx),
            startAngleDegrees = 270f,
            sweepAngleDegrees = 90f,
            forceMoveTo = false
        )
        lineTo(right, bottom)
        lineTo(left, bottom)
        close()
    }

    onDrawBehind {
        drawPath(path, color)
    }
}