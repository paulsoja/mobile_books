package com.spasinnya.mentoring.presentation.screens.authflow.register

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.model.Credentials
import com.spasinnya.mentoring.domain.model.Email
import com.spasinnya.mentoring.domain.model.Password
import com.spasinnya.mentoring.domain.usecase.auth.RegisterUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import com.spasinnya.mentoring.presentation.base.Validated
import com.spasinnya.mentoring.presentation.base.withLoading
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.DialogState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    private val savedState: SavedStateHandle
) : BaseMviViewModel<RegisterContract.State, RegisterContract.Event, RegisterContract.Effect>(initialState = RegisterContract.State()) {

    override fun handleEvent(event: RegisterContract.Event) {
        when (event) {
            is RegisterContract.Event.EmailChanged -> emailChanged(event.email)
            is RegisterContract.Event.PasswordChanged -> passwordChanged(event.password)
            is RegisterContract.Event.ValidateCredentials -> validateCredentials(
                email = event.email,
                password = event.password,
                onValid = ::register
            )
            is RegisterContract.Event.PasswordVisibilityToggled -> passwordVisibilityToggled(event.isPasswordVisible)
            RegisterContract.Event.DismissDialog -> hideDialog()
        }
    }

    private fun validateCredentials(email: Email, password: Password, onValid: (Credentials) -> Unit) {
        val creds = Credentials.of(email, password)
        when (creds) {
            is Validated.Valid -> onValid.invoke(creds.value)
            is Validated.Invalid -> {
                creds.errors.forEach { error ->
                        when (error) {
                            is Credentials.CredentialsError.Email -> setState { copy(emailError = error.error) }
                            is Credentials.CredentialsError.Password -> setState { copy(passwordError = error.error) }
                        }
                    }
            }
        }
    }

    private fun register(credentials: Credentials) = viewModelScope.launch(Dispatchers.IO) {
        registerUseCase.invoke(credentials)
            .withLoading { loading -> setState { copy(isLoading = loading) } }
            .collectLatest { result ->
                when (result) {
                    is Validated.Invalid -> handleDomainErrors(
                        errors = result.errors,
                        reduce = { errorType -> copy(dialog = DialogState.Shown(errorType)) }
                    )
                    is Validated.Valid -> {
                        sendEffect { RegisterContract.Effect.NavigateToOtp(credentials.email.value) }
                    }
                }
            }
    }

    private fun hideDialog() = setState { copy(dialog = DialogState.Hidden) }

    private fun passwordVisibilityToggled(visibility: Boolean) = setState { copy(isPasswordVisible = visibility.not()) }

    private fun passwordChanged(password: Password) = setState { copy(password = password, passwordError = Password.Error.NoError) }

    private fun emailChanged(email: Email) = setState { copy(email = email, emailError = Email.Error.NoError) }
}