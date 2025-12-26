package com.spasinnya.mentoring.presentation.screens.authflow.congrat

import androidx.lifecycle.viewModelScope
import com.spasinnya.mentoring.domain.usecase.auth.ChangeCongratsShownStatusUseCase
import com.spasinnya.mentoring.presentation.base.BaseMviViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CongratViewModel(
    private val changeCongratsShownStatusUseCase: ChangeCongratsShownStatusUseCase,
) : BaseMviViewModel<CongratContract.State, CongratContract.Event, CongratContract.Effect>(CongratContract.State()) {

    override fun handleEvent(event: CongratContract.Event) {
        when (event) {
            CongratContract.Event.ButtonClicked -> changeCongratsShownStatus()
        }
    }

    private fun changeCongratsShownStatus() = viewModelScope.launch {
        changeCongratsShownStatusUseCase.invoke()
            .collectLatest {
                sendEffect { CongratContract.Effect.NavigateToMain }
            }
    }
}