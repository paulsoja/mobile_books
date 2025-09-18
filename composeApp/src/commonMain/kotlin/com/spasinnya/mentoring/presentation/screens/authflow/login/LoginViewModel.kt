package com.spasinnya.mentoring.presentation.screens.authflow.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.model.Credentials
import com.spasinnya.mentoring.domain.model.Email
import com.spasinnya.mentoring.domain.model.Password
import com.spasinnya.mentoring.domain.model.UiErrorType
import com.spasinnya.mentoring.domain.rules.Validated
import com.spasinnya.mentoring.domain.usecase.auth.LoginUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import com.spasinnya.mentoring.presentation.base.loader
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val savedState: SavedStateHandle
) : BaseMviViewModel<LoginContract.State, LoginContract.Event, LoginContract.Effect>() {

    override fun createInitialState(): LoginContract.State = LoginContract.State()

    override fun handleEvent(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.EmailChanged -> setState { copy(email = Email(event.email)) }
            is LoginContract.Event.HandleError -> when (event.errorType) {
                is LoginContract.ErrorType.EmailError -> setState { copy(emailError = event.errorType.message) }
                LoginContract.ErrorType.NoConnection -> setState { copy(messageError = UiErrorType.NoConnection) }
                LoginContract.ErrorType.NoError -> setState {
                    copy(
                        messageError = null,
                        showAlertDialog = false,
                        emailError = Email.Error.No_error,
                        passwordError = Password.Error.No_error
                    )
                }
                is LoginContract.ErrorType.PasswordError -> setState { copy(passwordError = event.errorType.message) }
                LoginContract.ErrorType.UnexpectedError -> setState { copy(messageError = UiErrorType.Unexpected) }
            }
            is LoginContract.Event.PasswordChanged -> setState { copy(password = Password(event.password)) }
            is LoginContract.Event.ValidateCredentials -> {
                dispatchEvent(LoginContract.Event.HandleError(LoginContract.ErrorType.NoError))
                validateCredentials(
                    email = event.email,
                    password = event.password,
                    onValid = ::login
                )
            }
        }
    }

    private fun validateCredentials(email: String, password: String, onValid: (Credentials) -> Unit) {
        val creds = Credentials.of(email, password)
        when (creds) {
            is Validated.Valid -> onValid.invoke(creds.value)
            is Validated.Invalid -> {
                creds.errors
                    .also { Napier.d("validateCredentials: errors=$it") }
                    .forEach {
                        when (it) {
                            is Credentials.CredentialsError.Email -> {
                                dispatchEvent(LoginContract.Event.HandleError(LoginContract.ErrorType.EmailError(it.error)))
                            }
                            is Credentials.CredentialsError.Password -> {
                                dispatchEvent(LoginContract.Event.HandleError(LoginContract.ErrorType.PasswordError(it.error)))
                            }
                        }
                    }
            }
        }
    }

    private fun login(credentials: Credentials) = viewModelScope.launch(Dispatchers.IO) {
        loginUseCase.invoke(credentials)
            .loader(isLoading = { setState { copy(isLoading = it) } })
            .catch {
                handleFailures(it)
                Napier.d("login: catch=$it")
            }
            .collectLatest {
                Napier.d("login: success=$it")
                sendEffect { LoginContract.Effect.NavigateToMain }
            }
    }

    override fun handleNetworkError() {
        super.handleNetworkError()
        dispatchEvent(LoginContract.Event.HandleError(LoginContract.ErrorType.NoConnection))
    }

    override fun handleUnexpectedError(throwable: Throwable) {
        super.handleUnexpectedError(throwable)
        dispatchEvent(LoginContract.Event.HandleError(LoginContract.ErrorType.UnexpectedError))
    }

    override fun handleDomainError(statusCode: String) {
        super.handleDomainError(statusCode)
        when (statusCode) {
            "Bad Request" -> dispatchEvent(LoginContract.Event.HandleError(LoginContract.ErrorType.UnexpectedError))
        }
    }
}