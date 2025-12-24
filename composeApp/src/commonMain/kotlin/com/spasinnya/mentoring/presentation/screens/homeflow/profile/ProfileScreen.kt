package com.spasinnya.mentoring.presentation.screens.homeflow.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreOutlinedDropDown
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreOutlinedTextField
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreSpacerVerticalMedium
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTextBody
import com.spasinnya.mentoring.presentation.designsystem.composable.CoreTopAppBar
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputCommonDefaults
import com.spasinnya.mentoring.presentation.designsystem.defaults.InputEmailDefaults
import com.spasinnya.mentoring.presentation.di.viewModelFactory

@Composable
fun ProfileScreen(
    navigateBack: () -> Unit,
    navigateToChangePassword: () -> Unit,
) {
    val factory = remember {
        viewModelFactory { _, handle ->
            ProfileViewModel(savedStateHandle = handle)
        }
    }
    val viewModel: ProfileViewModel = viewModel(factory = factory)
    val state: ProfileContract.State by viewModel.state.collectAsStateWithLifecycle()

    val cityOptions = listOf("Київ", "Вишневе", "Боярка", "Святопетрівське")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFF5F7FC))
            .systemBarsPadding()
            .navigationBarsPadding()
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
                onValueChange = { viewModel.dispatchEvent(ProfileContract.Event.UpdateEmail(it)) },
                inputDefaults = InputEmailDefaults()
            )

            CoreSpacerVerticalMedium()

            ProfileFieldLabel(text = "Країна")
            CoreOutlinedDropDown(
                value = state.country,
                onValueChange = {},
                options = listOf("Україна"),
                enabled = false,
                inputDefaults = InputCommonDefaults(placeholder = "Україна")
            )

            CoreSpacerVerticalMedium()

            ProfileFieldLabel(text = "Місто")
            CoreOutlinedDropDown(
                value = state.city,
                onValueChange = { viewModel.dispatchEvent(ProfileContract.Event.UpdateCity(it)) },
                options = cityOptions,
                enabled = true,
                inputDefaults = InputCommonDefaults(placeholder = "Київ")
            )

            CoreSpacerVerticalMedium()

            ProfileFieldLabel(text = "Церква")
            CoreOutlinedTextField(
                value = state.church,
                onValueChange = { viewModel.dispatchEvent(ProfileContract.Event.UpdateChurch(it)) },
                inputDefaults = InputCommonDefaults(placeholder = "Спасіння")
            )

            CoreSpacerVerticalMedium()

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

            CoreSpacerVerticalMedium()
        }
    }
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
