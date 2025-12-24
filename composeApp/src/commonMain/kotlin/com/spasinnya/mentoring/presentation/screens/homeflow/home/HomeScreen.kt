package com.spasinnya.mentoring.presentation.screens.homeflow.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import books.composeapp.generated.resources.Res
import books.composeapp.generated.resources.ic_settings
import com.spasinnya.mentoring.presentation.base.rememberScreenModel
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTopBar
import com.spasinnya.mentoring.presentation.designsystem.composable.loading.LoadingOverlay
import com.spasinnya.mentoring.presentation.modals.SettingsModalBottomSheet
import kotlinx.coroutines.launch

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
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val (viewModel, state) = setupHomeScreenModel(
        navigateToLogin = navigateToLogin,
        showSnackbar = { message -> scope.launch { snackbarHostState.showSnackbar(message) } }
    )

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
            HomeContent(
                state = state,
                navigateToWeeks = navigateToWeeks,
                padding = padding,
                viewModel = viewModel,
            )

            LoadingOverlay(visible = state.isLoading)

            if (state.showSettingsDialog) {
                SettingsModalBottomSheet(
                    onClose = { viewModel.dispatchEvent(HomeContract.Event.ToggleSettingsDialog(false)) },
                    onLanguageChosen = { viewModel.dispatchEvent(HomeContract.Event.OnLanguageChosen(it)) },
                    onProfileClick = navigateToProfile,
                    onPromoCodesClick = navigateToPromoCodes,
                    onLogoutClick = { viewModel.dispatchEvent(HomeContract.Event.Logout) },
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
fun setupHomeScreenModel(
    navigateToLogin: () -> Unit,
    showSnackbar: (String) -> Unit,
): Pair<HomeViewModel, HomeContract.State> =
    rememberScreenModel<HomeViewModel, HomeContract.State, HomeContract.Effect>(
        create = { graph, handle ->
            HomeViewModel(
                logoutUseCase = graph.useCases.logoutUseCase,
                getBooksUseCase = graph.useCases.booksUseCase,
                purchaseBookUseCase = graph.useCases.purchaseBookUseCase,
                savedStateHandle = handle
            )
        },
        getState = { it.state },
        getEffect = { it.effect },
        onEffect = { effect ->
            when (effect) {
                HomeContract.Effect.NavigateToLogin -> navigateToLogin.invoke()
                is HomeContract.Effect.ShowSnackbar -> showSnackbar.invoke(effect.message)
            }
        }
    )