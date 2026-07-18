package com.spasinnya.mentoring.presentation.screens.homeflow.lessons

import androidx.compose.runtime.Composable
import com.spasinnya.mentoring.presentation.base.rememberScreenModel

import org.koin.core.parameter.parametersOf

@Composable
fun LessonsScreen(
    bookId: String,
    weekNumber: Int,
    navigateBack: () -> Unit,
) {
    val (viewModel, state) = setupLessonsScreenModel(
        bookId = bookId,
        weekNumber = weekNumber,
    )

    LessonsContent(
        bookId = bookId,
        state = state,
        onEvent = viewModel::dispatchEvent,
        navigateBack = navigateBack,
    )
}

@Composable
fun setupLessonsScreenModel(
    bookId: String,
    weekNumber: Int,
): Pair<LessonsViewModel, LessonsContract.State> =
    rememberScreenModel<LessonsViewModel, LessonsContract.State, LessonsContract.Effect>(
        parameters = { parametersOf(bookId, weekNumber) }
    )
