package com.spasinnya.mentoring.presentation.screens.authflow.newpassword

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.spasinnya.mentoring.domain.model.Email
import com.spasinnya.mentoring.domain.model.OtpCode
import com.spasinnya.mentoring.domain.model.Password
import com.spasinnya.mentoring.domain.model.PasswordConfirmation
import com.spasinnya.mentoring.domain.model.ResetPasswordCredentials
import com.spasinnya.mentoring.domain.usecase.auth.ResetPasswordUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import com.spasinnya.mentoring.presentation.base.Validated
import com.spasinnya.mentoring.presentation.base.withLoading
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.DialogState
import com.spasinnya.mentoring.presentation.model.UiErrorType
import com.spasinnya.mentoring.presentation.navigation.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewPasswordViewModel(
    private val resetPasswordUseCase: ResetPasswordUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseMviViewModel<NewPasswordContract.State, NewPasswordContract.Event, NewPasswordContract.Effect>(
    initialState = NewPasswordContract.State()
) {

    private val route = savedStateHandle.toRoute<Screen.AuthFlow.NewPasswordScreen>()
    private val email = Email.raw(route.email)
    private val otp = OtpCode.raw(route.code)

    override fun handleEvent(event: NewPasswordContract.Event) {
        when (event) {
            is NewPasswordContract.Event.PasswordChanged -> passwordChanged(event.password)
            is NewPasswordContract.Event.ConfirmationChanged -> confirmationChanged(event.confirmation)
            is NewPasswordContract.Event.PasswordVisibilityToggled -> passwordVisibilityToggled(event.isVisible)
            is NewPasswordContract.Event.ConfirmationVisibilityToggled -> confirmationVisibilityToggled(event.isVisible)
            NewPasswordContract.Event.SaveClicked -> validateCredentials(onValid = ::resetPassword)
            NewPasswordContract.Event.DismissDialog -> hideDialog()
        }
    }

    private fun validateCredentials(onValid: (ResetPasswordCredentials) -> Unit) {
        val credentials = ResetPasswordCredentials.of(
            email = email,
            otp = otp,
            password = state.value.password,
            confirmation = state.value.confirmation
        )

        when (credentials) {
            is Validated.Valid -> onValid.invoke(credentials.value)
            is Validated.Invalid -> when (val error = credentials.error) {
                is ResetPasswordCredentials.ResetPasswordError.InvalidPassword ->
                    setState { copy(passwordError = error.error) }

                is ResetPasswordCredentials.ResetPasswordError.InvalidConfirmation ->
                    setState { copy(confirmationError = error.error) }

                is ResetPasswordCredentials.ResetPasswordError.InvalidEmail,
                is ResetPasswordCredentials.ResetPasswordError.InvalidOtp ->
                    setState { copy(dialog = DialogState.Shown(UiErrorType.Client)) }
            }
        }
    }

    private fun resetPassword(credentials: ResetPasswordCredentials) = viewModelScope.launch(Dispatchers.IO) {
        resetPasswordUseCase.invoke(credentials)
            .withLoading { loading -> setState { copy(isLoading = loading) } }
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
                    is Validated.Valid -> sendEffect { NewPasswordContract.Effect.NavigateToLogin }
                }
            }
    }

    private fun hideDialog() = setState { copy(dialog = DialogState.Hidden) }

    private fun passwordVisibilityToggled(isVisible: Boolean) = setState { copy(isPasswordVisible = isVisible.not()) }

    private fun confirmationVisibilityToggled(isVisible: Boolean) = setState { copy(isConfirmationVisible = isVisible.not()) }

    private fun passwordChanged(password: Password) =
        setState { copy(password = password, passwordError = Password.Error.NoError) }

    private fun confirmationChanged(confirmation: PasswordConfirmation) =
        setState { copy(confirmation = confirmation, confirmationError = PasswordConfirmation.Error.NoError) }
}
