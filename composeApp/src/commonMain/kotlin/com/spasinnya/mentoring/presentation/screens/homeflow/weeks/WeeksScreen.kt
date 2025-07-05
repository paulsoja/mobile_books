package com.spasinnya.mentoring.presentation.screens.homeflow.weeks

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Badge
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import androidx.lifecycle.viewmodel.compose.viewModel
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.ic_back
import books.composeapp.generated.resources.ic_check
import com.spasinnya.mentoring.presentation.di.viewmodelfactory.createWeeksViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.vectorResource

@Composable
fun WeeksScreen(
    navigateBack: () -> Unit,
) {

    val viewModel: WeeksViewModel = viewModel(factory = createWeeksViewModel)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF5F7FC))
            .systemBarsPadding()
            .navigationBarsPadding()
    ) {
        TopAppBar(
            title = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Тиждень 1",
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFFDFA672),
                            fontSize = 14.sp
                        )
                    )
                    Text(
                        text = "«Особисте життя з Богом»",
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF3C4E73),
                            fontSize = 16.sp
                        )
                    )
                }
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
            backgroundColor = Color(0xFFF5F7FC),
            elevation = 0.dp
        )

        val tabs = remember { listOf("1", "2", "3", "4", "5") }
        val scope = rememberCoroutineScope()

        val pagerState = rememberPagerState(
            initialPage = 0,
            pageCount = { tabs.size }
        )

        Spacer(modifier = Modifier.height(8.dp))
        TabRow(
            modifier = Modifier.fillMaxWidth().height(78.dp).padding(horizontal = 14.dp),
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = Color(0xFFF5F7FC),
            indicator = { tabPositions -> },
            divider = { },
            contentColor = Color(0xFF3C4E73),
            tabs = {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 2.dp)
                            .padding(top = 8.dp)
                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                            .tabBackground(
                                color = if (pagerState.currentPage == index) Color.White else Color(0xFFE7F2F8),
                                topPadding = 8.dp,
                                radius = 16.dp
                            ),
                        selected = pagerState.currentPage == index,
                        onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                        text = {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.Bottom,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                if (index == 0 || index == 1) {
                                    Badge(
                                        modifier = Modifier
                                            .width(28.dp)
                                            .height(16.dp)
                                            .clip(RoundedCornerShape(16.dp))
                                            .background(color = Color(0xFFDFA672)),
                                        backgroundColor = if (pagerState.currentPage == index) Color(0xFFDFA672) else Color.Transparent
                                    ) {
                                        Icon(
                                            imageVector = vectorResource(Res.drawable.ic_check),
                                            contentDescription = "",
                                            tint = Color.White,
                                            modifier = Modifier.size(12.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                Column(
                                    modifier = Modifier,
                                    verticalArrangement = Arrangement.Bottom,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Урок",
                                        style = MaterialTheme.typography.body1.copy(
                                            fontWeight = FontWeight.Normal,
                                            color = Color(0xFF96A4BA),
                                            fontSize = 12.sp,
                                            lineHeight = 16.sp
                                        )
                                    )
                                    Text(
                                        text = "${index + 1}",
                                        style = MaterialTheme.typography.body1.copy(
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
                Text(
                    text = "Урок 1: «Особиста молитва»",
                    style = MaterialTheme.typography.body1.copy(
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
                        Text(
                            text = "\uD83D\uDCD6",
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 32.sp
                            )
                        )
                        Text(
                            text = "Давайте разом прочитаємо уривок із Біблії, який записано у 1 посланні до Коринтян 4:3-4",
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFCF7D47),
                                fontSize = 16.sp,
                                lineHeight = 24.sp
                            )
                        )
                    }
                    Text(
                        text = "\"Тож Мені найменше залежить, як про мене судите ви або який взагалі людський суд, бо я і сам себе не суджу. І хоч я ні в чому не відчуваю себе винним, але цим я не є виправданий. Адже Той, Хто мене судить, то Господь\"",
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFCF7D47),
                            fontSize = 18.sp,
                            lineHeight = 28.sp
                        )
                    )
                }

                Text(
                    text = "День у юдеїв починався о шостій годині ранку й закінчувався о шостій вечора. Набожний юдей молився тричі: о девʼятій годині ранку, о дванадцятій і о третій годині дня. Вони вважали, що молитва дієва в будь-якому місці, але в храмі вона має подвійну силу. Дуже цікаво, що Петро та Іван дотримувалися старих звичаїв, живучи під благодаттю.",
                    style = MaterialTheme.typography.body1.copy(
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
            // Начинаем с левого верхнего скругленного угла
            moveTo(left, top + radiusPx)
            arcTo(
                rect = Rect(left, top, left + 2 * radiusPx, top + 2 * radiusPx),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            // Верхняя линия
            lineTo(right - radiusPx, top)
            // Правый верхний скругленный угол
            arcTo(
                rect = Rect(right - 2 * radiusPx, top, right, top + 2 * radiusPx),
                startAngleDegrees = 270f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            // Правая линия вниз
            lineTo(right, bottom)
            // Нижняя линия
            lineTo(left, bottom)
            // Левая линия вверх
            close()
        }
        drawPath(path, color)
    }
)
