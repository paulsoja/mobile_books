package com.spasinnya.mentoring.presentation.screens.homeflow.lessons

import androidx.compose.runtime.Composable
import com.spasinnya.mentoring.presentation.base.rememberScreenModel

@Composable
fun LessonsScreen(
    weekId: Int,
    navigateBack: () -> Unit,
) {
    val (viewModel, state) = setupLessonsScreenModel(
        weekId = weekId,
    )

    LessonsContent(
        state = state,
        navigateBack = navigateBack,
    )
}

@Composable
fun setupLessonsScreenModel(
    weekId: Int,
): Pair<LessonsViewModel, LessonsContract.State> =
    rememberScreenModel<LessonsViewModel, LessonsContract.State, LessonsContract.Effect>(
        create = { graph, handle ->
            LessonsViewModel(
                weekId = weekId,
                lessonsUseCase = graph.useCases.lessonsUseCase,
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
