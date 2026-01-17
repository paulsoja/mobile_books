package com.spasinnya.mentoring.data.repository

import com.spasinnya.mentoring.data.storage.datastore.AppStore
import com.spasinnya.mentoring.data.storage.datastore.LocaleStore
import com.spasinnya.mentoring.data.storage.datastore.TokenStore
import com.spasinnya.mentoring.domain.repository.ChangeCongratsShownRepository
import com.spasinnya.mentoring.domain.repository.CongratsShownRepository
import com.spasinnya.mentoring.domain.repository.LocaleRepository
import com.spasinnya.mentoring.domain.repository.SetLocaleRepository
import com.spasinnya.mentoring.domain.repository.TokenRepository
import com.spasinnya.mentoring.domain.rules.DomainError
import com.spasinnya.mentoring.presentation.base.Validated
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

fun tokens(
    tokenStore: TokenStore
): TokenRepository = {
    tokenStore.flow().map { token ->
        token?.let { Validated.Valid(it) }
            ?: Validated.Invalid(listOf(DomainError.NotFound))
    }
}

fun shownCongrats(
    appStore: AppStore
): CongratsShownRepository = {
    appStore.flow().map { Validated.Valid(it) }
}

fun changeShownCongratsStatus(
    appStore: AppStore
): ChangeCongratsShownRepository = {
    flow {
        appStore.save(true)
        emit(Validated.Valid(Unit))
    }
}

fun getAppLocale(
    localeStore: LocaleStore
): LocaleRepository = {
    localeStore.flow()
        .map { Validated.Valid(it) }
        .catch { Validated.Invalid(listOf(DomainError.NotFound)) }
}

fun setAppLocale(
    localeStore: LocaleStore
): SetLocaleRepository = { languageTag ->
    localeStore.save(languageTag)
    flowOf(Validated.Valid(Unit))
}