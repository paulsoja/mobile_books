package com.spasinnya.mentoring.presentation.di.viewmodelfactory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

inline fun <reified T : ViewModel> create(crossinline initBlock: () -> T): ViewModelProvider.Factory {
    return viewModelFactory {
        initializer {
            initBlock.invoke()
        }
    }
}

inline fun <reified T : ViewModel, D> createWithState(
    deps: D,
    crossinline initBlock: (D, SavedStateHandle) -> T
): ViewModelProvider.Factory = viewModelFactory {
    initializer {
        val handle = createSavedStateHandle()
        initBlock(deps, handle)
    }
}