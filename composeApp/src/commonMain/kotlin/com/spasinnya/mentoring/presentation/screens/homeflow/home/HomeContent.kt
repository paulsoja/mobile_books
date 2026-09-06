package com.spasinnya.mentoring.presentation.screens.homeflow.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spasinnya.mentoring.domain.model.BookMeta
import com.spasinnya.mentoring.generated.resources.Res
import com.spasinnya.mentoring.generated.resources.common_buy
import com.spasinnya.mentoring.generated.resources.common_content
import com.spasinnya.mentoring.generated.resources.common_open
import com.spasinnya.mentoring.generated.resources.home_choose_book
import com.spasinnya.mentoring.generated.resources.home_no_books_message
import com.spasinnya.mentoring.generated.resources.home_no_books_title
import com.spasinnya.mentoring.generated.resources.ic_arrow_right
import com.spasinnya.mentoring.generated.resources.ic_content
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreCircularProgressIndicator
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreOutlinedButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CorePrimaryButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextScreenTitle
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextSubtitle
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextTitle
import com.spasinnya.mentoring.presentation.designsystem.composable.states.EmptyState
import com.spasinnya.mentoring.presentation.designsystem.coverPainter
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeContent(
    books: List<BookMeta>,
    isPurchaseLoading: Boolean,
    navigateToWeeks: (bookId: String, bookNumber: Int) -> Unit,
    padding: PaddingValues,
    onEvent: (HomeContract.Event) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(top = padding.calculateTopPadding()).padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        CoreTextScreenTitle(
            text = stringResource(Res.string.home_choose_book),
            modifier = Modifier.padding(horizontal = 24.dp),
        )
        if (books.isEmpty()) {
            EmptyState(
                modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp).padding(bottom = 40.dp),
                title = stringResource(Res.string.home_no_books_title),
                message = stringResource(Res.string.home_no_books_message)
            )
        } else {
            Pager(
                list = books,
                pageContent = { _: PagerState, item: BookMeta ->
                    PagerCard(
                        item = item,
                        isLoading = isPurchaseLoading,
                        onPurchaseClicked = {
                            onEvent(HomeContract.Event.PurchaseBook(item.id))
                        },
                        onOpenClicked = {
                            navigateToWeeks.invoke(item.id, item.bookNumber)
                        }
                    )
                },
                indicatorContent = { pageCount, currentPage ->
                    Row(
                        Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                            .background(color = Color.Transparent)
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(
                            12.dp,
                            Alignment.CenterHorizontally
                        )
                    ) {
                        PagerIndicator(
                            modifier = Modifier,
                            pageCount = pageCount,
                            currentPage = currentPage
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun Pager(
    list: List<BookMeta>,
    pageContent: @Composable (pagerState: PagerState, item: BookMeta) -> Unit,
    indicatorContent: @Composable (pageCount: Int, currentPage: Int) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        val pagerState = rememberPagerState(pageCount = { list.size })
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 24.dp),
            pageSpacing = 8.dp,
            key = {
                it
            }
        ) { page ->
            Card(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(bottom = 70.dp)
                    .shadow(
                        elevation = 32.dp,
                        shape = RoundedCornerShape(40.dp),
                        clip = true,
                        ambientColor = Color(0xFF3C4F73),
                        spotColor = Color(0xFF3C4F73)
                    ),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(40.dp)
            ) {
                pageContent.invoke(pagerState, list[page])
            }
        }
        indicatorContent.invoke(
            pagerState.pageCount,
            pagerState.currentPage
        )
    }
}

@Composable
private fun PagerCard(
    item: BookMeta,
    isLoading: Boolean,
    onPurchaseClicked: (bookId: String) -> Unit,
    onOpenClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        PageItem(
            item = item,
            isLoading = isLoading,
            onContentClicked = {},
            onPurchaseClicked = onPurchaseClicked,
            onOpenClicked = onOpenClicked
        )
    }
}

@Composable
private fun PageItem(
    item: BookMeta,
    isLoading: Boolean,
    onContentClicked: () -> Unit,
    onPurchaseClicked: (bookId: String) -> Unit,
    onOpenClicked: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = coverPainter(bookNumber = item.bookNumber),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 2f),
            contentScale = ContentScale.FillWidth
        )
        Column(modifier = Modifier.fillMaxSize().padding(all = 16.dp)) {
            CoreTextTitle(text = item.title)
            CoreTextSubtitle(text = item.subtitle)
            Spacer(modifier = Modifier.height(12.dp))
            CoreTextBody(
                text = item.description,
                modifier = Modifier.fillMaxWidth().weight(1f).verticalScroll(rememberScrollState())
            )
            Spacer(modifier = Modifier.height(12.dp))
            BookCardBottomBar(
                item = item,
                isLoading = isLoading,
                onContentClicked = onContentClicked,
                onPurchaseClicked = onPurchaseClicked,
                onOpenClicked = onOpenClicked
            )
        }
    }
}

@Composable
private fun PagerIndicator(
    modifier: Modifier = Modifier,
    pageCount: Int,
    currentPage: Int
) {
    repeat(pageCount) { iteration ->
        val backgroundColor by animateColorAsState(
            targetValue = if (currentPage == iteration) Color(0xFF3C4E73) else Color.White,
            animationSpec = tween(durationMillis = 400)
        )

        val textColor by animateColorAsState(
            targetValue = if (currentPage != iteration) Color(0xFF3C4E73) else Color.White,
            animationSpec = tween(durationMillis = 800)
        )

        Box(
            modifier = modifier
                .clip(CircleShape)
                .background(backgroundColor)
                .size(24.dp),
            contentAlignment = Alignment.Center
        ) {
            CoreTextBody(
                text = "${iteration + 1}",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = textColor,
                    fontSize = 12.sp
                )
            )
        }
    }
}

@Composable
fun BookCardBottomBar(
    item: BookMeta,
    isLoading: Boolean,
    onContentClicked: () -> Unit,
    onPurchaseClicked: (bookId: String) -> Unit,
    onOpenClicked: () -> Unit,
) = Row(
    modifier = Modifier.fillMaxWidth().animateContentSize(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(16.dp)
) {
    Box(modifier = Modifier.padding(start = 8.dp)) {
        if (item.isPurchased) {
            CircularProgressBar(
                percentage = item.progress,
                modifier = Modifier.size(52.dp)
            )
        } else {
            CoreOutlinedButton(
                modifier = Modifier.fillMaxWidth(.5f).height(68.dp),
                text = stringResource(Res.string.common_content),
                iconAfter = Res.drawable.ic_content,
                onClick = { onContentClicked() }
            )
        }
    }
    if (isLoading) {
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            CoreCircularProgressIndicator()
        }
    } else {
        CorePrimaryButton(
            modifier = Modifier.weight(1f).height(68.dp),
            text = if (item.isPurchased) stringResource(Res.string.common_open) else stringResource(Res.string.common_buy),
            onClick = { if (item.isPurchased) onOpenClicked() else onPurchaseClicked(item.id) },
            iconAfter = if (item.isPurchased) Res.drawable.ic_arrow_right else null
        )
    }
}

@Composable
fun CircularProgressBar(
    percentage: Float, // 0f..1f
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 12.dp,
    backgroundColor: Color = Color(0xFFEAEFF5),
    progressColor: Color = Color(0xFFD2A676),
    textColor: Color = Color.Black
) {
    val sweepAngle = remember(percentage) { percentage * 360f }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.aspectRatio(1f)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val diameter = size.minDimension
            val topLeft = Offset(
                (size.width - diameter) / 2f,
                (size.height - diameter) / 2f
            )
            val size = Size(diameter, diameter)

            // Background circle
            drawArc(
                color = backgroundColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = size,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )

            // Foreground arc
            drawArc(
                color = progressColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = topLeft,
                size = size,
                style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }

        CoreTextBody(
            text = "${(percentage * 100).toInt()}%",
            style = MaterialTheme.typography.bodySmall.copy(
                color = textColor,
                fontSize = 12.sp
            )
        )
    }
}