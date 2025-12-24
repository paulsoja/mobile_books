package com.spasinnya.mentoring.presentation.screens.homeflow.lessons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.ic_check
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreBadge
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTopAppBar
import kotlinx.coroutines.launch

@Composable
fun LessonsContent(
    state: LessonsContract.State,
    navigateBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF5F7FC))
            .systemBarsPadding()
            .navigationBarsPadding()
    ) {
        CoreTopAppBar(
            title = "Тиждень 1",
            subtitle = "«Особисте життя з Богом»",
            onBackClick = navigateBack
        )

        val scope = rememberCoroutineScope()

        val pagerState = rememberPagerState(
            initialPage = 0,
            pageCount = { state.lessons.size }
        )

        Spacer(modifier = Modifier.height(8.dp))
        TabRow(
            modifier = Modifier.fillMaxWidth().height(78.dp).padding(horizontal = 8.dp),
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color(0xFFF5F7FC),
            indicator = { tabPositions -> },
            divider = { },
            contentColor = Color(0xFF3C4E73),
            tabs = {
                state.lessons.forEachIndexed { index, lesson ->
                    Tab(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 2.dp)
                            .padding(top = 8.dp)
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                            .tabBackground(
                                color = if (pagerState.currentPage == index) Color.White else Color(0xFFE7F2F8),
                                topPadding = 24.dp,
                                radius = 16.dp
                            ),
                        selected = pagerState.currentPage == index,
                        onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                        text = {
                            Column(
                                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                                verticalArrangement = Arrangement.Bottom,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (index == 0 || index == 1) {
                                    CoreBadge(
                                        iconRes = Res.drawable.ic_check,
                                        backgroundColor = if (pagerState.currentPage == index) Color(0xFFDFA672) else Color.Transparent
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                Column(
                                    modifier = Modifier,
                                    verticalArrangement = Arrangement.Bottom,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    CoreTextBody(
                                        text = "Урок",
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontWeight = FontWeight.Normal,
                                            color = if (pagerState.currentPage == index) Color(0xFFDFA672) else Color(0xFF96A4BA),
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
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    )
                }
            }
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f).background(color = Color.White),
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
                        verticalAlignment = Alignment.Top
                    ) {
                        CoreTextBody(
                            text = "\uD83D\uDCD6",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 32.sp
                            )
                        )
                        CoreTextBody(
                            text = "Давайте разом прочитаємо уривок із Біблії, який записано у 1 посланні до Коринтян 4:3-4",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFCF7D47),
                                fontSize = 16.sp,
                                lineHeight = 24.sp
                            )
                        )
                    }
                    CoreTextBody(
                        text = state.lessons[page].quote.orEmpty(),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFCF7D47),
                            fontSize = 18.sp,
                            lineHeight = 28.sp
                        )
                    )
                }

                state.lessons[page].content.forEach { content ->
                    CoreTextBody(
                        text = content.data,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF54595F),
                            fontSize = 18.sp,
                            lineHeight = 28.sp
                        )
                    )
                }
            }
        }
    }
}

fun Modifier.tabBackground(
    color: Color,
    topPadding: Dp,
    radius: Dp
) = this.then(
    Modifier.drawBehind {
        val topPaddingPx = topPadding.toPx()
        val radiusPx = radius.toPx()
        val width = size.width
        val height = size.height - topPaddingPx
        val left = 0f
        val top = topPaddingPx
        val right = width
        val bottom = top + height

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
        drawPath(path, color)
    }
)