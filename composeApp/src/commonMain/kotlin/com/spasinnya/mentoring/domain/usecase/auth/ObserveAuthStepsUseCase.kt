package com.spasinnya.mentoring.domain.usecase.auth

import com.spasinnya.mentoring.domain.model.AuthSteps
import com.spasinnya.mentoring.domain.model.DomainError
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.usecase.profile.GetProfileUseCase
import com.spasinnya.mentoring.presentation.base.Validated
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

/**
 * The stored token only proves a session existed. This confirms the server still honors it before
 * the app commits to a signed-in destination, and reports back when that answer can't be obtained.
 */
class ObserveAuthStepsUseCase(
    private val checkAuthStepsUseCase: CheckAuthStepsUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
) {
    operator fun invoke(): Flow<DomainResult<AuthSteps>> = flow {
        var probed = false

        checkAuthStepsUseCase.invoke().collect { result ->
            if (probed.not() && result.needsLiveSession()) {
                probed = true

                when (val probe = probeSession()) {
                    SessionProbe.Alive -> Unit

                    // The rejection already cleared the stored token, which comes back through this
                    // same flow as AuthSteps.Auth, so the stale verdict is simply dropped.
                    SessionProbe.Rejected -> return@collect

                    // The token still works but the account behind it is gone, so the session has
                    // to be torn down here - nothing else will do it.
                    SessionProbe.AccountGone -> {
                        logoutUseCase.invoke().collect()
                        return@collect
                    }

                    is SessionProbe.Unreachable -> {
                        emit(Validated.Invalid(probe.error))
                        return@collect
                    }
                }
            }
            emit(result)
        }
    }

    private suspend fun probeSession(): SessionProbe =
        when (val result = getProfileUseCase.invoke().first()) {
            is Validated.Valid -> SessionProbe.Alive
            is Validated.Invalid -> when (result.error) {
                DomainError.Unauthorized -> SessionProbe.Rejected

                // Depending on the error body a missing account arrives as either of these.
                DomainError.NotFound,
                DomainError.UserNotFound -> SessionProbe.AccountGone

                else -> SessionProbe.Unreachable(result.error)
            }
        }
}

private sealed interface SessionProbe {
    data object Alive : SessionProbe

    /** The server refused the token and the failed refresh already cleared the session. */
    data object Rejected : SessionProbe

    /** The account was deleted, so a still-valid token has to be discarded deliberately. */
    data object AccountGone : SessionProbe

    /** The session could be neither confirmed nor denied, so nothing about it may be assumed. */
    data class Unreachable(val error: DomainError) : SessionProbe
}

private fun DomainResult<AuthSteps>.needsLiveSession(): Boolean =
    this is Validated.Valid && (value == AuthSteps.Home || value == AuthSteps.Congrats)
