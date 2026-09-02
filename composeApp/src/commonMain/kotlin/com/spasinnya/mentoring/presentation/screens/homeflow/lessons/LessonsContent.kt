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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spasinnya.mentoring.domain.mapper.asAnnotatedString
import com.spasinnya.mentoring.domain.model.LessonBlock
import com.spasinnya.mentoring.domain.model.ParsedLesson
import com.spasinnya.mentoring.generated.resources.Res
import com.spasinnya.mentoring.generated.resources.common_confirm
import com.spasinnya.mentoring.generated.resources.common_week
import com.spasinnya.mentoring.generated.resources.ic_check
import com.spasinnya.mentoring.generated.resources.lessons_lesson
import com.spasinnya.mentoring.generated.resources.lessons_practical_work
import com.spasinnya.mentoring.presentation.designsystem.AppResources
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreBadge
import com.spasinnya.mentoring.presentation.designsystem.composable.CorePrimaryButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreText
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTopAppBar
import com.spasinnya.mentoring.presentation.designsystem.composable.loading.LoadingOverlay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

private val HighlightColor = Color.Green

@Composable
fun LessonsContent(
    bookId: String,
    state: LessonsContract.State,
    onEvent: (LessonsContract.Event) -> Unit,
    navigateBack: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFF5F7FC))
                .statusBarsPadding()
        ) {
            CoreTopAppBar(
                title = stringResource(Res.string.common_week) + " " + state.weekNumber,
                subtitle = state.weekTitle,
                onBackClick = navigateBack
            )

            val pagerState = rememberPagerState(
                initialPage = 0,
                pageCount = { state.lessons.size }
            )

            Spacer(modifier = Modifier.height(8.dp))

            LessonTabs(
                pagerState = pagerState,
                lessons = state.lessons,
                completedLessons = state.completedLessons
            )

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .weight(1f)
                    .background(color = Color.White),
                contentPadding = PaddingValues(horizontal = 0.dp),
                pageSpacing = 8.dp,
                key = { page -> state.lessons[page].lessonNumber },
                beyondViewportPageCount = 1
            ) { page ->
                val lesson = state.lessons[page]

                LessonPage(
                    lesson = lesson,
                    hasHomework = lesson.lessonNumber in state.lessonsWithHomework,
                    onOpenPracticalWork = {
                        onEvent(LessonsContract.Event.OpenPracticalWork(lesson.lessonNumber))
                    }
                )
            }

            state.practicalWorkLesson?.let { lessonNumber ->
                PracticalWorkModalBottomSheet(
                    bookId = bookId,
                    weekNumber = state.weekNumber,
                    lessonNumber = lessonNumber,
                    onClose = { onEvent(LessonsContract.Event.ClosePracticalWork) },
                    onSaved = { completedLessons ->
                        onEvent(LessonsContract.Event.UpdateCompletedLessons(completedLessons))
                    }
                )
            }
        }

        LoadingOverlay(visible = state.isLoading)
    }
}

@Composable
private fun LessonTabs(
    pagerState: PagerState,
    lessons: List<ParsedLesson>,
    completedLessons: List<Int>,
) {
    val scope = rememberCoroutineScope()

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

                lessons.forEachIndexed { index, lesson ->
                    val isSelected = pagerState.currentPage == index
                    val isCompleted = lesson.lessonNumber in completedLessons

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
                                        text = stringResource(Res.string.lessons_lesson),
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
                                            backgroundColor = Color(0xFFDFA672)
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
}

@Composable
private fun LessonPage(
    lesson: ParsedLesson,
    hasHomework: Boolean,
    onOpenPracticalWork: () -> Unit,
) {
    val content = rememberLessonPageContent(lesson)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        item(key = "title", contentType = "title") {
            CoreTextBody(
                text = lesson.title,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3C4E73),
                    fontSize = 20.sp
                )
            )
        }

        item(key = "quotes", contentType = "quotes") {
            LessonQuotes(
                leadQuote = content.leadQuote,
                secondaryQuote = content.secondaryQuote
            )
        }

        itemsIndexed(
            items = content.blocks,
            key = { index, _ -> "block_$index" },
            contentType = { _, block -> block::class }
        ) { _, block ->
            LessonBlockItem(block = block)
        }

        item(key = "action", contentType = "action") {
            CorePrimaryButton(
                text = if (hasHomework) {
                    stringResource(Res.string.lessons_practical_work)
                } else {
                    stringResource(Res.string.common_confirm)
                },
                onClick = { if (hasHomework) onOpenPracticalWork() }
            )
        }
    }
}

@Composable
private fun rememberLessonPageContent(lesson: ParsedLesson): LessonPageContent =
    remember(lesson) {
        LessonPageContent(
            leadQuote = lesson.quotes.getOrNull(0)?.asAnnotatedString(HighlightColor),
            secondaryQuote = lesson.quotes.getOrNull(1)?.asAnnotatedString(HighlightColor),
            blocks = lesson.blocks.map(::toRenderableBlock)
        )
    }

private fun toRenderableBlock(block: LessonBlock): RenderableBlock = when (block) {
    is LessonBlock.Paragraph -> RenderableBlock.Paragraph(
        text = block.paragraph.asAnnotatedString(HighlightColor),
        centered = false
    )

    is LessonBlock.CenterText -> RenderableBlock.Paragraph(
        text = block.paragraph.asAnnotatedString(HighlightColor),
        centered = true
    )

    is LessonBlock.Divider -> RenderableBlock.Divider
    is LessonBlock.Image -> RenderableBlock.Image(block.path)
    is LessonBlock.Table -> RenderableBlock.Table
}

@Immutable
private data class LessonPageContent(
    val leadQuote: AnnotatedString?,
    val secondaryQuote: AnnotatedString?,
    val blocks: List<RenderableBlock>,
)

@Immutable
private sealed interface RenderableBlock {
    data class Paragraph(val text: AnnotatedString, val centered: Boolean) : RenderableBlock
    data class Image(val path: String) : RenderableBlock
    data object Divider : RenderableBlock
    data object Table : RenderableBlock
}

@Composable
private fun LessonQuotes(
    leadQuote: AnnotatedString?,
    secondaryQuote: AnnotatedString?,
) {
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
                text = "📖",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                )
            )

            leadQuote?.let { quote ->
                CoreTextBody(
                    text = quote,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFCF7D47),
                        fontSize = 16.sp,
                        lineHeight = 24.sp
                    )
                )
            }
        }

        secondaryQuote?.let { quote ->
            CoreTextBody(
                text = quote,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFCF7D47),
                    fontSize = 18.sp,
                    lineHeight = 28.sp
                )
            )
        }
    }
}

@Composable
private fun LessonBlockItem(block: RenderableBlock) {
    when (block) {
        is RenderableBlock.Paragraph -> {
            CoreText(
                text = block.text,
                style = paragraphTextStyle(),
                textAlign = if (block.centered) TextAlign.Center else TextAlign.Start,
                modifier = if (block.centered) Modifier.fillMaxWidth() else Modifier,
            )
        }

        is RenderableBlock.Divider -> {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        is RenderableBlock.Image -> {
            Image(
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth,
                contentDescription = null,
                painter = painterResource(AppResources.drawable(block.path)),
            )
        }

        is RenderableBlock.Table -> {
            CoreText(
                text = "table",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().height(100.dp).background(Color.Green)
                    .padding(top = 12.dp),
            )
        }
    }
}

@Composable
private fun paragraphTextStyle(): TextStyle {
    val base = MaterialTheme.typography.bodySmall
    return remember(base) {
        base.copy(
            fontWeight = FontWeight.Normal,
            color = Color(0xFF54595F),
            fontSize = 18.sp,
            lineHeight = 28.sp
        )
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
