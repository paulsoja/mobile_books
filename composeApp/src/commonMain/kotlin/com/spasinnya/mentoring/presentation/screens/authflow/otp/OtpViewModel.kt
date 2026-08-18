package com.spasinnya.mentoring.presentation.screens.authflow.otp

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.spasinnya.mentoring.domain.enums.OtpPurpose
import com.spasinnya.mentoring.domain.model.Email
import com.spasinnya.mentoring.domain.model.OtpCode
import com.spasinnya.mentoring.domain.model.OtpCredentials
import com.spasinnya.mentoring.domain.usecase.auth.ConfirmOtpCodeUseCase
import com.spasinnya.mentoring.domain.usecase.auth.RequestOtpCodeUseCase
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
    private val requestOtpUseCase: RequestOtpCodeUseCase,
    private val otpCodeUseCase: ConfirmOtpCodeUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseMviViewModel<OtpContract.State, OtpContract.Event, OtpContract.Effect>(
    initialState = savedStateHandle.toRoute<Screen.AuthFlow.OtpScreen>().let { route ->
        OtpContract.State(
            email = when (val validated = Email.of(route.email)) {
                is Validated.Valid -> validated.value
                is Validated.Invalid -> error("OtpViewModel got invalid email: $validated")
            },
            purpose = OtpPurpose.fromName(route.purpose)
        )
    }
) {

    override fun handleEvent(event: OtpContract.Event) {
        when (event) {
            is OtpContract.Event.OtpChanged -> otpChanged(event.otp)
            is OtpContract.Event.ConfirmClicked -> validateCredentials(
                email = event.email,
                code = event.otp,
                onValid = ::submitOtp
            )

            OtpContract.Event.DismissDialog -> hideDialog()
            OtpContract.Event.RequestOtp -> requestOtp()
        }
    }

    init {
        dispatchEvent(OtpContract.Event.RequestOtp)
    }

    private fun requestOtp() = viewModelScope.launch(Dispatchers.IO) {
        requestOtpUseCase.invoke(email = state.value.email, purpose = state.value.purpose)
            .withLoading {  }
            .collectLatest { result ->
                when (result) {
                    is Validated.Invalid -> handleDomainErrors(
                        error = result.error,
                        reduce = { errorType -> copy(dialog = DialogState.Shown(errorType)) }
                    )
                    is Validated.Valid -> Unit //TODO start timer
                }
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
                setState { copy(otpError = result.error) }
            }
        }
    }

    private fun submitOtp(otpCredentials: OtpCredentials) {
        when (state.value.purpose) {
            OtpPurpose.LOGIN -> confirmOtp(otpCredentials)
            // Password reset validates the code server-side in /reset-password, so verifying it
            // here would consume it and sign the user in before the new password is set.
            OtpPurpose.PASSWORD_RESET -> sendEffect {
                OtpContract.Effect.NavigateToNewPassword(
                    email = otpCredentials.email.value,
                    code = otpCredentials.otp.value
                )
            }
        }
    }

    private fun confirmOtp(otpCredentials: OtpCredentials) = viewModelScope.launch(Dispatchers.IO) {
        otpCodeUseCase.invoke(otpCredentials)
            .withLoading { setState { copy(isLoading = it) } }
            .collectLatest { result ->
                when (result) {
                    is Validated.Invalid -> handleDomainErrors(
                        error = result.error,
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
