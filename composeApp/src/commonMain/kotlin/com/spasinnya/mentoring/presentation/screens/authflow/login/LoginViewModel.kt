package com.spasinnya.mentoring.presentation.screens.authflow.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.model.Credentials
import com.spasinnya.mentoring.domain.model.Email
import com.spasinnya.mentoring.domain.model.Password
import com.spasinnya.mentoring.domain.usecase.auth.LoginUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import com.spasinnya.mentoring.presentation.base.Validated
import com.spasinnya.mentoring.presentation.base.withLoading
import com.spasinnya.mentoring.presentation.designsystem.composable.dialog.DialogState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val savedState: SavedStateHandle
) : BaseMviViewModel<LoginContract.State, LoginContract.Event, LoginContract.Effect>(LoginContract.State()) {

    override fun handleEvent(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.EmailChanged -> emailChanged(event.email)
            is LoginContract.Event.PasswordChanged -> passwordChanged(event.password)
            is LoginContract.Event.LoginClicked -> validateCredentials(
                email = event.email,
                password = event.password,
                onValid = ::login
            )

            is LoginContract.Event.PasswordVisibilityToggled -> passwordVisibilityToggled(event.isPasswordVisible)
            LoginContract.Event.DismissDialog -> hideDialog()
        }
    }

    private fun validateCredentials(
        email: Email,
        password: Password,
        onValid: (Credentials) -> Unit
    ) {
        when (val creds = Credentials.of(email, password)) {
            is Validated.Valid -> onValid(creds.value)

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

    private fun login(credentials: Credentials) = viewModelScope.launch(Dispatchers.IO) {
        loginUseCase.invoke(credentials)
            .withLoading { loading -> setState { copy(isLoading = loading) } }
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
                    is Validated.Valid -> sendEffect { LoginContract.Effect.NavigateToMain }
                }
            }
    }

    private fun hideDialog() = setState { copy(dialog = DialogState.Hidden) }

    private fun passwordVisibilityToggled(visibility: Boolean) = setState { copy(isPasswordVisible = visibility.not()) }

    private fun passwordChanged(password: Password) = setState { copy(password = password, passwordError = Password.Error.NoError) }

    private fun emailChanged(email: Email) = setState { copy(email = email, emailError = Email.Error.NoError) }
}