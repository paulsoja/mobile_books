package com.spasinnya.mentoring.presentation.homeflow.home

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.ic_content
import books.composeapp.generated.resources.ic_logo
import books.composeapp.generated.resources.ic_settings
import books.composeapp.generated.resources.img_cover_01
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun HomeScreen(
    navigateToSettings: () -> Unit
) {

    val viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)

    Scaffold(
        modifier = Modifier.fillMaxSize().systemBarsPadding().navigationBarsPadding(),
        backgroundColor = Color(0xFFF5F7FC),
        topBar = {
            TopBar(onActionClicked = navigateToSettings)
        },
        content = {
            Column(
                modifier = Modifier.fillMaxSize().padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    text = "Оберіть Наставництво \uD83D\uDCDA",
                    modifier = Modifier.padding(horizontal = 24.dp),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3C4E73)
                )
                Pager(
                    pageContent = { pagerState: PagerState, page: Int ->
                        PagerCard(pagerState, page)
                    },
                    indicatorContent = { pageCount, currentPage ->
                        Row(
                            Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .background(color = Color.Transparent)
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 24.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
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
    )
}

@Composable
private fun TopBar(
    onActionClicked: () -> Unit
) = Row(
    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp).padding(end = 8.dp),
    verticalAlignment = Alignment.CenterVertically
) {
    IconButton(onClick = onActionClicked) {
        Icon(
            imageVector = vectorResource(Res.drawable.ic_settings),
            contentDescription = "",
            tint = Color(0xFF3C4E73),
            modifier = Modifier.size(32.dp)
        )
    }
    Spacer(modifier = Modifier.weight(1f))
    Icon(
        imageVector = vectorResource(Res.drawable.ic_logo),
        contentDescription = "",
        tint = Color.Unspecified,
        modifier = Modifier.height(40.dp)
    )
}

@Composable
private fun Pager(
    pageContent: @Composable (pagerState: PagerState, page: Int) -> Unit,
    indicatorContent: @Composable (pageCount: Int, currentPage: Int) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        val pagerState = rememberPagerState(pageCount = { 4 })
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 24.dp),
            pageSpacing = 8.dp
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
                backgroundColor = Color.White,
                shape = RoundedCornerShape(40.dp)
            ) {
                pageContent.invoke(pagerState, page)
            }
        }
        indicatorContent.invoke(
            pagerState.pageCount,
            pagerState.currentPage
        )
    }
}

@Composable
private fun PagerCard(pagerState: PagerState, page: Int) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        PageItem()
    }
}

@Composable
private fun PageItem() {
    Column(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(Res.drawable.img_cover_01),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Column(modifier = Modifier.fillMaxSize().padding(all = 16.dp)) {
            Text(
                text = "Наставництво - I",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3C4E73)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Ця книга призначена для духовного зростання новонавернених через наставництво у групах спілкування з першого дня після покаяння до вступу в завіт із Богом через водне хрещення.\n" +
                        "Її можна також використовувати як курс із підготовки новонавернених до водного хрещення через семінарські заняття.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF717A88)
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f).height(68.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFF3C4E73),
                        backgroundColor = Color.Transparent
                    ),
                    border = BorderStroke(width = 1.dp, color = Color(0xFF3C4E73)),
                    shape = RoundedCornerShape(40.dp),
                    onClick = {},
                ) {
                    Text(
                        "Зміст",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_content),
                        contentDescription = "",
                        modifier = Modifier.size(24.dp)
                    )
                }
                Button(
                    modifier = Modifier.weight(1f).height(68.dp),
                    shape = RoundedCornerShape(40.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF3C4E73),
                        contentColor = Color.White
                    ),
                    onClick = {}
                ) {
                    Text(
                        "Купити",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
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
            Text(
                text = "${iteration + 1}",
                color = textColor,
                fontSize = 12.sp
            )
        }
    }
}