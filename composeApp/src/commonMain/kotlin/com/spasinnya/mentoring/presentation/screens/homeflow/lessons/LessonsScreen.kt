package com.spasinnya.mentoring.presentation.screens.homeflow.lessons

import androidx.compose.runtime.Composable
import com.spasinnya.mentoring.presentation.base.rememberScreenModel

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
        state = state,
        navigateBack = navigateBack,
    )
}

@Composable
fun setupLessonsScreenModel(
    bookId: String,
    weekNumber: Int,
): Pair<LessonsViewModel, LessonsContract.State> =
    rememberScreenModel<LessonsViewModel, LessonsContract.State, LessonsContract.Effect>(
        create = { graph, handle ->
            LessonsViewModel(
                bookId = bookId,
                weekNumber = weekNumber,
                importBookFromMarkdownUseCase = graph.useCases.importBookFromMarkdownUseCase,
                savedStateHandle = handle
            )
        },
        getState = { it.state },
        getEffect = { it.effect },
        onEffect = { effect ->
            when (effect) {

                else -> {}
            }
        }
    )
