package com.spasinnya.mentoring.presentation.screens.authflow.register

import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.model.Credentials
import com.spasinnya.mentoring.domain.model.Email
import com.spasinnya.mentoring.domain.model.Password
import com.spasinnya.mentoring.domain.rules.Validated
import com.spasinnya.mentoring.domain.usecase.RegisterUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import com.spasinnya.mentoring.presentation.base.loader
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
            is RegisterContract.Event.ValidateCredentials -> validateCredentials(
                email = event.email,
                password = event.password,
                onValid = ::register
            )
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
                            setState { copy(status = RegisterContract.Status.Error(RegisterContract.ErrorType.EmailError(it.error))) }
                        }
                        is Credentials.CredentialsError.Password -> {
                            setState { copy(status = RegisterContract.Status.Error(RegisterContract.ErrorType.PasswordError(it.error))) }
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
            }
            .collectLatest {
                // success
            }
    }

    override fun handleNetworkError() {
        super.handleNetworkError()
        setState { copy(status = RegisterContract.Status.Error(RegisterContract.ErrorType.NoConnection))}
    }

    override fun handleUnexpectedError(throwable: Throwable) {
        super.handleUnexpectedError(throwable)
        setState { copy(status = RegisterContract.Status.Error(RegisterContract.ErrorType.UnexpectedError))}
    }

    override fun handleDomainError(statusCode: String) {
        super.handleDomainError(statusCode)
        when (statusCode) {

        }
    }
}