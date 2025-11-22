package com.spasinnya.mentoring.presentation.screens.homeflow.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.systemBarsPadding
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.ic_content
import books.composeapp.generated.resources.ic_settings
import books.composeapp.generated.resources.img_cover_01
import com.spasinnya.mentoring.domain.model.ShortBook
import com.spasinnya.mentoring.presentation.base.CollectEffects
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreCircularProgressIndicator
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreOutlinedButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CorePrimaryButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextScreenTitle
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextTitle
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTopBar
import com.spasinnya.mentoring.presentation.di.viewModelFactory
import com.spasinnya.mentoring.presentation.modals.SettingsModalBottomSheet
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable
fun HomeScreen(
    navigateToProfile: () -> Unit,
    navigateToPromoCodes: () -> Unit,
    navigateToAuthors: () -> Unit,
    navigateToSpasinnyaBooks: () -> Unit,
    navigateToSpasinnyaChurch: () -> Unit,
    navigateToWeeks: (bookId: Int, bookNumber: String) -> Unit,
    navigateToLogin: () -> Unit,
) {
    val factory = remember {
        viewModelFactory { graph, handle ->
            HomeViewModel(
                logoutUseCase = graph.useCases.logoutUseCase,
                getBooksUseCase = graph.useCases.booksUseCase,
                purchaseBookUseCase = graph.useCases.purchaseBookUseCase,
                savedStateHandle = handle
            )
        }
    }
    val viewModel: HomeViewModel = viewModel(factory = factory)
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    viewModel.effect.CollectEffects { effect ->
        when (effect) {
            HomeContract.Effect.NavigateToLogin -> navigateToLogin.invoke()
            is HomeContract.Effect.ShowSnackbar -> scope.launch {
                snackbarHostState.showSnackbar(effect.message)
            }
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize().systemBarsPadding(),
        containerColor = Color(0xFFF5F7FC),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        },
        topBar = {
            CoreTopBar(
                actionIcon = Res.drawable.ic_settings,
                onActionClicked = { viewModel.dispatchEvent(HomeContract.Event.ToggleSettingsDialog(true)) }
            )
        },
        content = { padding ->
            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0x66F5F7FC))
                        .zIndex(4f),
                    contentAlignment = Alignment.Center
                ) {
                    CoreCircularProgressIndicator()
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize().padding(top = padding.calculateTopPadding()).padding(top = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    CoreTextScreenTitle(
                        text = "Оберіть Наставництво \uD83D\uDCDA",
                        modifier = Modifier.padding(horizontal = 24.dp).clickable { viewModel.logout() },
                    )
                    Pager(
                        list = state.books,
                        pageContent = { pagerState: PagerState, item: ShortBook ->
                            PagerCard(
                                item = item,
                                isLoading = state.isPurchaseLoading,
                                onPurchaseClicked = {
                                    viewModel.dispatchEvent(
                                        HomeContract.Event.PurchaseBook(item.id)
                                    )
                                },
                                onOpenClicked = {
                                    navigateToWeeks.invoke(item.id, item.number)
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

            if (state.showSettingsDialog) {
                SettingsModalBottomSheet(
                    onClose = { viewModel.dispatchEvent(HomeContract.Event.ToggleSettingsDialog(false)) },
                    onLanguageChosen = { viewModel.dispatchEvent(HomeContract.Event.OnLanguageChosen(it)) },
                    onProfileClick = navigateToProfile,
                    onPromoCodesClick = navigateToPromoCodes,
                    onLogoutClick = navigateToLogin,
                    onAuthorsClick = navigateToAuthors,
                    onSpasinnyaBooksClick = navigateToSpasinnyaBooks,
                    onSpasinnyaChurchClick = navigateToSpasinnyaChurch,
                    selectedLanguage = state.selectedLanguage
                )
            }
        }
    )
}

@Composable
private fun Pager(
    list: List<ShortBook>,
    pageContent: @Composable (pagerState: PagerState, item: ShortBook) -> Unit,
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
    item: ShortBook,
    isLoading: Boolean,
    onPurchaseClicked: (bookId: Int) -> Unit,
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
    item: ShortBook,
    isLoading: Boolean,
    onContentClicked: () -> Unit,
    onPurchaseClicked: (bookId: Int) -> Unit,
    onOpenClicked: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(Res.drawable.img_cover_01),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Column(modifier = Modifier.fillMaxSize().padding(all = 16.dp)) {
            CoreTextTitle(
                text = item.title
            )
            Spacer(modifier = Modifier.height(12.dp))
            CoreTextBody(
                text = item.subtitle,
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
    item: ShortBook,
    isLoading: Boolean,
    onContentClicked: () -> Unit,
    onPurchaseClicked: (bookId: Int) -> Unit,
    onOpenClicked: () -> Unit,
) = Row(
    modifier = Modifier.fillMaxWidth().animateContentSize(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(16.dp)
) {
    Box(modifier = Modifier.padding(start = 8.dp)) {
        if (item.isPurchased) {
            CircularProgressBar(
                percentage = 0.42f,
                modifier = Modifier.size(52.dp)
            )
        } else {
            CoreOutlinedButton(
                modifier = Modifier.fillMaxWidth(.5f).height(68.dp),
                text = "Зміст",
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
            text = if (item.isPurchased) "Відкрити" else "Купити",
            onClick = { if (item.isPurchased) onOpenClicked() else onPurchaseClicked(item.id) }
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