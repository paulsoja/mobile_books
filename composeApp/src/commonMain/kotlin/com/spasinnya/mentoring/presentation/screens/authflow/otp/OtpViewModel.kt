package com.spasinnya.mentoring.presentation.screens.authflow.otp

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.spasinnya.mentoring.domain.model.Email
import com.spasinnya.mentoring.domain.model.OtpCode
import com.spasinnya.mentoring.domain.model.OtpCredentials
import com.spasinnya.mentoring.domain.usecase.auth.ConfirmOtpCodeUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import com.spasinnya.mentoring.presentation.base.Validated
import com.spasinnya.mentoring.presentation.base.withLoading
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.DialogState
import com.spasinnya.mentoring.presentation.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OtpViewModel(
    private val otpCodeUseCase: ConfirmOtpCodeUseCase,
    private val savedStateHandle: SavedStateHandle,
) : BaseMviViewModel<OtpContract.State, OtpContract.Event, OtpContract.Effect>(
    initialState = OtpContract.State(
        email = run {
            val rawEmail = savedStateHandle.toRoute<Screen.AuthFlow.OtpScreen>().email
            when (val validated = Email.of(rawEmail)) {
                is Validated.Valid -> validated.value
                is Validated.Invalid -> error("OtpViewModel got invalid email: $validated")
            }
        }
    )
) {

    override fun handleEvent(event: OtpContract.Event) {
        when (event) {
            is OtpContract.Event.OtpChanged -> otpChanged(event.otp)
            is OtpContract.Event.ConfirmClicked -> validateCredentials(
                email = event.email,
                code = event.otp,
                onValid = ::confirmOtp
            )

            OtpContract.Event.DismissDialog -> hideDialog()
        }
    }

    private fun validateCredentials(email: Email.Valid, code: OtpCode, onValid: (OtpCredentials) -> Unit) {
        when (val result = code.validate()) {
            is Validated.Valid -> {
                val validOtp = result.value
                val creds = OtpCredentials(email = email, otp = validOtp)
                onValid(creds)
            }
            is Validated.Invalid -> {
                val firstError = result.errors.firstOrNull() ?: OtpCode.Error.NotFilled
                setState { copy(otpError = firstError) }
            }
        }
    }

    private fun confirmOtp(otpCredentials: OtpCredentials) = viewModelScope.launch(Dispatchers.IO) {
        otpCodeUseCase.invoke(otpCredentials)
            .withLoading { setState { copy(isLoading = it) } }
            .collectLatest { result ->
                when (result) {
                    is Validated.Invalid -> handleDomainErrors(
                        errors = result.errors,
                        reduce = { errorType ->
                            copy(
                                isLoading = false,
                                dialog = DialogState.Shown(errorType)
                            )
                        }
                    )
                    is Validated.Valid -> sendEffect { OtpContract.Effect.NavigateToCongratScreen }
                }
            }
    }

    private fun hideDialog() = setState { copy(dialog = DialogState.Hidden) }

    private fun otpChanged(otp: OtpCode) = setState { copy(otp = otp, otpError = OtpCode.Error.NoError) }
}