package com.spasinnya.mentoring.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
open class BaseViewModel : ViewModel() {

    companion object {
        inline fun <reified VM : ViewModel> factory(
            crossinline creator: () -> VM
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: KClass<T>, extras: CreationExtras): T {
                if (modelClass.isInstance(VM::class)) {
                    return creator() as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}