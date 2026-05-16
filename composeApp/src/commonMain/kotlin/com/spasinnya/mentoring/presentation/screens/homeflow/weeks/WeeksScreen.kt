package com.spasinnya.mentoring.presentation.screens.homeflow.weeks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.spasinnya.mentoring.generated.resources.Res
import com.spasinnya.mentoring.generated.resources.common_mentorship
import com.spasinnya.mentoring.generated.resources.ic_content
import com.spasinnya.mentoring.presentation.base.rememberScreenModel
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreIconButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTopAppBar
import org.jetbrains.compose.resources.stringResource
import org.koin.core.parameter.parametersOf

@Composable
fun WeeksScreen(
    bookNumber: Int,
    bookId: String,
    navigateBack: () -> Unit,
    onActionClicked: () -> Unit,
    navigateToLessons: (bookId: String, weekNumber: Int) -> Unit
) {
    val (viewModel, state) = setupWeeksScreenModel(bookId)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = listOf(Color(0xFFF5F7FC), Color(0xFFE7F2F8))))
            .systemBarsPadding()
            .navigationBarsPadding(),
        containerColor = Color.Transparent,
        topBar = {
            CoreTopAppBar(
                title = stringResource(Res.string.common_mentorship) + " - $bookNumber",
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
            WeeksContent(
                bookNumber = bookNumber,
                state = state,
                paddingValues = it,
                navigateToLessons = {
                    navigateToLessons.invoke(bookId, it)
                }
            )
        }
    )
}

@Composable
fun setupWeeksScreenModel(
    bookId: String
): Pair<WeeksViewModel, WeeksContract.State> =
    rememberScreenModel<WeeksViewModel, WeeksContract.State, WeeksContract.Effect>(
        parameters = { parametersOf(bookId) }
    )