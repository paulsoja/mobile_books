package com.spasinnya.mentoring.presentation.di.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

inline fun <reified T : ViewModel> create(crossinline initBlock: () -> T): ViewModelProvider.Factory {
    return viewModelFactory {
        initializer {
            initBlock.invoke()
        }
    }
}