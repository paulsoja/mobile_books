package com.spasinnya.mentoring.presentation.screens.authflow.otp

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.spasinnya.mentoring.domain.model.Email
import com.spasinnya.mentoring.domain.model.OtpCode
import com.spasinnya.mentoring.domain.model.OtpCredentials
import com.spasinnya.mentoring.domain.model.UiErrorType
import com.spasinnya.mentoring.domain.rules.Validated
import com.spasinnya.mentoring.domain.usecase.auth.ConfirmOtpCodeUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import com.spasinnya.mentoring.presentation.base.loader
import com.spasinnya.mentoring.presentation.navigation.Screen
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class OtpViewModel(
    private val otpCodeUseCase: ConfirmOtpCodeUseCase,
    private val savedStateHandle: SavedStateHandle,
) : BaseMviViewModel<OtpContract.State, OtpContract.Event, OtpContract.Effect>() {

    override fun createInitialState(): OtpContract.State = OtpContract.State()

    init {
        val args = savedStateHandle.toRoute<Screen.AuthFlow.OtpScreen>()
        setState { copy(email = Email(args.email)) }
    }

    override fun handleEvent(event: OtpContract.Event) {
        when (event) {
            is OtpContract.Event.CodeChanged -> setState { copy(code = OtpCode(event.code)) }
            is OtpContract.Event.HandleError -> when (event.errorType) {
                OtpContract.ErrorType.NoConnection -> setState { copy(messageError = UiErrorType.NoConnection) }
                OtpContract.ErrorType.NoError -> setState {
                    copy(
                        messageError = null,
                        showAlertDialog = false,
                    )
                }
                is OtpContract.ErrorType.OtpCodeError -> setState { copy(otpError = event.errorType.message) }
                OtpContract.ErrorType.UnexpectedError -> setState { copy(messageError = UiErrorType.Unexpected) }
                is OtpContract.ErrorType.EmailError -> Unit
            }
            is OtpContract.Event.ValidateOtpCredentials -> {
                dispatchEvent(OtpContract.Event.HandleError(OtpContract.ErrorType.NoError))
                validateCredentials(
                    email = event.email,
                    code = event.code,
                    onValid = ::confirmOtp
                )
            }
        }
    }

    private fun validateCredentials(email: String, code: String, onValid: (OtpCredentials) -> Unit) {
        val creds = OtpCredentials.of(email, code)
        when (creds) {
            is Validated.Valid -> onValid.invoke(creds.value)
            is Validated.Invalid -> {
                creds.errors
                    .also { Napier.d("validateCredentials: errors=$it") }
                    .forEach {
                        when (it) {
                            is OtpCredentials.OtpCredentialsError.Email -> {
                                dispatchEvent(OtpContract.Event.HandleError(OtpContract.ErrorType.EmailError(it.error)))
                            }
                            is OtpCredentials.OtpCredentialsError.Code -> {
                                dispatchEvent(OtpContract.Event.HandleError(OtpContract.ErrorType.OtpCodeError(it.error)))
                            }
                        }
                    }
            }
        }
    }

    private fun confirmOtp(otpCredentials: OtpCredentials) = viewModelScope.launch(Dispatchers.IO) {
        otpCodeUseCase.invoke(otpCredentials)
            .loader(isLoading = { setState { copy(isLoading = it) } })
            .catch {
                handleFailures(it)
                Napier.d("otp: catch=$it")
            }
            .collectLatest {
                Napier.d("otp: success=$it")
                sendEffect { OtpContract.Effect.NavigateToCongratScreen }
            }
    }

    override fun handleNetworkError() {
        super.handleNetworkError()
        dispatchEvent(OtpContract.Event.HandleError(OtpContract.ErrorType.NoConnection))
    }

    override fun handleUnexpectedError(throwable: Throwable) {
        super.handleUnexpectedError(throwable)
        dispatchEvent(OtpContract.Event.HandleError(OtpContract.ErrorType.UnexpectedError))
    }

    override fun handleDomainError(statusCode: String, code: Int) {
        super.handleDomainError(statusCode, code)
        when (statusCode) {
            "Bad Request" -> dispatchEvent(OtpContract.Event.HandleError(OtpContract.ErrorType.UnexpectedError))
            "Invalid code" -> dispatchEvent(OtpContract.Event.HandleError(OtpContract.ErrorType.OtpCodeError(OtpCode.Error.Invalid_format)))
        }
    }
}