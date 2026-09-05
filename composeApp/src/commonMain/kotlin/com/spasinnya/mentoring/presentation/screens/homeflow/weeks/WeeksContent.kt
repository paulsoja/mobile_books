package com.spasinnya.mentoring.presentation.screens.homeflow.weeks

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spasinnya.mentoring.domain.model.BookWeek
import com.spasinnya.mentoring.generated.resources.Res
import com.spasinnya.mentoring.generated.resources.common_completed
import com.spasinnya.mentoring.generated.resources.common_week
import com.spasinnya.mentoring.generated.resources.ic_chevron_right
import com.spasinnya.mentoring.generated.resources.weeks_empty_message
import com.spasinnya.mentoring.generated.resources.weeks_empty_title
import com.spasinnya.mentoring.presentation.designsystem.Primary900
import com.spasinnya.mentoring.presentation.designsystem.Secondary300
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreCardWithContent
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreLinearProgressIndicator
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreText
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBadge
import com.spasinnya.mentoring.presentation.designsystem.composable.states.EmptyState
import com.spasinnya.mentoring.presentation.designsystem.coverPainter
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun WeeksContent(
    bookNumber: Int,
    state: WeeksContract.State,
    paddingValues: PaddingValues,
    onEvent: (WeeksContract.Event) -> Unit,
    navigateToLessons: (weekId: Int) -> Unit
) {
    val weeks = state.bookMeta?.tableOfContents.orEmpty()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 24.dp)
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            modifier = Modifier.fillMaxWidth().height(100.dp).clip(RoundedCornerShape(16.dp)),
            painter = coverPainter(bookNumber = bookNumber),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        PullToRefreshBox(
            modifier = Modifier.fillMaxSize(),
            isRefreshing = state.isRefreshing,
            onRefresh = { onEvent(WeeksContract.Event.Refresh) }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 40.dp)
            ) {
                when {
                    weeks.isNotEmpty() -> items(weeks, key = { it.weekNumber }) { week ->
                        WeekCard(
                            week = week,
                            onClick = { navigateToLessons.invoke(week.weekNumber) }
                        )
                    }

                    !state.isLoading -> item {
                        EmptyState(
                            modifier = Modifier.fillParentMaxSize(),
                            title = stringResource(Res.string.weeks_empty_title),
                            message = stringResource(Res.string.weeks_empty_message)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun WeekCard(
    week: BookWeek,
    onClick: () -> Unit
) {
    CoreCardWithContent(
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        contentPadding = 0.dp,
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 8.dp, top = 12.dp, bottom = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CoreText(
                            text = "${stringResource(Res.string.common_week)} ${week.weekNumber}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Secondary300,
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp,
                                lineHeight = 18.sp
                            )
                        )
                        if (week.isCompleted) {
                            CoreTextBadge(text = stringResource(Res.string.common_completed))
                        }
                    }
                    CoreText(
                        text = "«${week.weekTitle}»",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Primary900,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            lineHeight = 18.sp
                        )
                    )
                }
                if (week.progress > 0f) {
                    CoreLinearProgressIndicator(progress = week.progress)
                }
            }
            Icon(
                imageVector = vectorResource(Res.drawable.ic_chevron_right),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
