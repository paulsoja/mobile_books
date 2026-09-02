package com.spasinnya.mentoring.presentation.screens.homeflow.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spasinnya.mentoring.presentation.base.rememberScreenModel
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreOutlinedTextField
import com.spasinnya.mentoring.presentation.designsystem.composable.CorePrimaryButton
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalLarge
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalMedium
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTopAppBar
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.AppAlertDialog
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.toAlertTexts
import com.spasinnya.mentoring.presentation.designsystem.composable.loading.LoadingOverlay
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputCommonDefaults
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputEmailDefaults
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navigateBack: () -> Unit,
    navigateToChangePassword: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val (viewModel, state) = rememberScreenModel<ProfileViewModel, ProfileContract.State, ProfileContract.Effect>(
        onEffect = { effect ->
            when (effect) {
                ProfileContract.Effect.ProfileSaved ->
                    scope.launch { snackbarHostState.showSnackbar("Профіль збережено") }

                ProfileContract.Effect.SaveFailed ->
                    scope.launch { snackbarHostState.showSnackbar("Не вдалося зберегти профіль") }

                ProfileContract.Effect.NavigateToChangePassword -> Unit
            }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF5F7FC))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            CoreTopAppBar(
                title = "Мій профайл",
                onBackClick = navigateBack
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            ) {
                ProfileFieldLabel(text = "Ім\"я")
                CoreOutlinedTextField(
                    value = state.firstName,
                    onValueChange = { viewModel.dispatchEvent(ProfileContract.Event.UpdateFirstName(it)) },
                    inputDefaults = InputCommonDefaults(placeholder = "Максим")
                )

                CoreSpacerVerticalMedium()

                ProfileFieldLabel(text = "Прізвище")
                CoreOutlinedTextField(
                    value = state.lastName,
                    onValueChange = { viewModel.dispatchEvent(ProfileContract.Event.UpdateLastName(it)) },
                    inputDefaults = InputCommonDefaults(placeholder = "Яковлєв")
                )

                CoreSpacerVerticalMedium()

                ProfileFieldLabel(text = "Email")
                CoreOutlinedTextField(
                    value = state.email,
                    onValueChange = {},
                    enabled = false,
                    inputDefaults = InputEmailDefaults()
                )

                CoreSpacerVerticalMedium()

//                 ProfileFieldLabel(text = "Країна")
//                 CoreOutlinedDropDown(
//                     value = state.country,
//                     onValueChange = {},
//                     options = listOf(state.country),
//                     enabled = false,
//                     inputDefaults = InputCommonDefaults(placeholder = "Україна")
//                 )
//
//                 CoreSpacerVerticalMedium()
//
//                 ProfileFieldLabel(text = "Місто")
//                 CoreOutlinedDropDown(
//                     value = state.city,
//                     onValueChange = {},
//                     options = listOf(state.city),
//                     enabled = false,
//                     inputDefaults = InputCommonDefaults(placeholder = "Київ")
//                 )
//
//                 CoreSpacerVerticalMedium()
//
//                 ProfileFieldLabel(text = "Церква")
//                 CoreOutlinedTextField(
//                     value = state.church,
//                     onValueChange = {},
//                     enabled = false,
//                     inputDefaults = InputCommonDefaults(placeholder = "Спасіння")
//                 )
//
//                 CoreSpacerVerticalMedium()

                CoreTextBody(
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = navigateToChangePassword
                        )
                        .padding(vertical = 8.dp),
                    text = "Змінити пароль",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFF3C4E73),
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    ),
                    textDecoration = TextDecoration.Underline,
                )

                CoreSpacerVerticalLarge()

                CorePrimaryButton(
                    text = "Зберегти",
                    enabled = !state.isLoading,
                    onClick = { viewModel.dispatchEvent(ProfileContract.Event.SaveProfile) }
                )

                CoreSpacerVerticalMedium()
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
        )
    }

    LoadingOverlay(visible = state.isLoading)

    AppAlertDialog(
        state = state.dialog,
        mapTexts = { it.toAlertTexts() },
        onDismiss = { viewModel.dispatchEvent(ProfileContract.Event.DismissDialog) },
        onConfirm = { viewModel.dispatchEvent(ProfileContract.Event.DismissDialog) },
    )
}

@Composable
private fun ProfileFieldLabel(text: String) {
    CoreTextBody(
        modifier = Modifier.fillMaxWidth().padding(bottom = 4.dp),
        text = text,
        style = MaterialTheme.typography.bodySmall.copy(
            color = Color(0xFF3C4E73),
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        )
    )
}
