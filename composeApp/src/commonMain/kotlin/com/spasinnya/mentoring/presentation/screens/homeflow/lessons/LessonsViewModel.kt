package com.spasinnya.mentoring.presentation.screens.homeflow.lessons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

class LessonsViewModel() : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                LessonsViewModel()
            }
        }
    }
}