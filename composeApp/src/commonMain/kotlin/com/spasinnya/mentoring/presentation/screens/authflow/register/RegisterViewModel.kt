package com.spasinnya.mentoring.presentation.screens.authflow.register

import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.model.Credentials
import com.spasinnya.mentoring.domain.model.Email
import com.spasinnya.mentoring.domain.model.Password
import com.spasinnya.mentoring.domain.model.UiErrorType
import com.spasinnya.mentoring.domain.rules.Validated
import com.spasinnya.mentoring.domain.usecase.RegisterUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import com.spasinnya.mentoring.presentation.base.loader
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase
) : BaseMviViewModel<RegisterContract.State, RegisterContract.Event, RegisterContract.Effect>() {

    override fun createInitialState(): RegisterContract.State = RegisterContract.State()

    override fun handleEvent(event: RegisterContract.Event) {
        when (event) {
            is RegisterContract.Event.EmailChanged -> setState { copy(email = Email(event.email)) }
            is RegisterContract.Event.PasswordChanged -> setState { copy(password = Password(event.password)) }
            is RegisterContract.Event.ValidateCredentials -> {
                dispatchEvent(RegisterContract.Event.HandleError(RegisterContract.ErrorType.NoError))
                validateCredentials(
                    email = event.email,
                    password = event.password,
                    onValid = ::register
                )
            }

            is RegisterContract.Event.HandleError -> when (event.errorType) {
                is RegisterContract.ErrorType.EmailError -> setState { copy(emailError = event.errorType.message) }
                RegisterContract.ErrorType.NoConnection -> setState { copy(messageError = UiErrorType.NoConnection) }
                RegisterContract.ErrorType.NoError -> setState { copy(messageError = null, showAlertDialog = false, emailError = Email.Error.No_error, passwordError = Password.Error.No_error) }
                is RegisterContract.ErrorType.PasswordError -> setState { copy(passwordError = event.errorType.message) }
                RegisterContract.ErrorType.UnexpectedError -> setState { copy(messageError = UiErrorType.Unexpected) }
            }
        }
    }

    private fun validateCredentials(email: String, password: String, onValid: (Credentials) -> Unit) {
        val creds = Credentials.of(email, password)
        when (creds) {
            is Validated.Valid -> onValid.invoke(creds.value)
            is Validated.Invalid -> {
                creds.errors.forEach {
                    when (it) {
                        is Credentials.CredentialsError.Email -> {
                            dispatchEvent(RegisterContract.Event.HandleError(RegisterContract.ErrorType.EmailError(it.error)))
                        }
                        is Credentials.CredentialsError.Password -> {
                            dispatchEvent(RegisterContract.Event.HandleError(RegisterContract.ErrorType.PasswordError(it.error)))
                        }
                    }
                }
            }
        }
    }

    private fun register(credentials: Credentials) = viewModelScope.launch(Dispatchers.IO) {
        registerUseCase.invoke(credentials)
            .loader(isLoading = { setState { copy(isLoading = it) } })
            .catch {
                handleFailures(it)
                Napier.d("register: catch=$it")
            }
            .collectLatest {
                // success
                Napier.d("register: success=$it")
            }
    }

    override fun handleNetworkError() {
        super.handleNetworkError()
        dispatchEvent(RegisterContract.Event.HandleError(RegisterContract.ErrorType.NoConnection))
    }

    override fun handleUnexpectedError(throwable: Throwable) {
        super.handleUnexpectedError(throwable)
        dispatchEvent(RegisterContract.Event.HandleError(RegisterContract.ErrorType.UnexpectedError))
    }

    override fun handleDomainError(statusCode: String) {
        super.handleDomainError(statusCode)
        when (statusCode) {
            "Bad Request" -> dispatchEvent(RegisterContract.Event.HandleError(RegisterContract.ErrorType.UnexpectedError))
        }
    }
}