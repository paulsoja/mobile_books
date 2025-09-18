package com.spasinnya.mentoring.presentation.di

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spasinnya.mentoring.data.storage.datastore.TokenStore

data class AppGraph(
    val tokenStore: TokenStore,
    val http: io.ktor.client.HttpClient,
    val repos: RepoModule,
    val useCases: UseCaseModule
)

expect fun provideAppGraph(appContext: Any): AppGraph

/*
* Reset app graph
* When to call resetAppGraph()

Full logout / account switch — in order to:
    - close the old HttpClient;
    - clear any interceptors/caches;
    - rebuild the TokenStore / UseCases for the new user.

After calling resetAppGraph(), the next ViewModel created via viewModelFactory will automatically receive a fresh graph.
* */
expect fun resetAppGraph()

typealias ViewModelBuilder<T> = (graph: AppGraph, handle: SavedStateHandle) -> T

expect inline fun <reified T : ViewModel> viewModelFactory(
    crossinline builder: ViewModelBuilder<T>
): ViewModelProvider.Factory
