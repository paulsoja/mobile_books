package com.spasinnya.mentoring.domain.repository

import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.Token
import kotlinx.coroutines.flow.Flow

interface PrefRepository {
    fun getToken(): Flow<DomainResult<Token>>
    fun getCongratsShown(): Flow<DomainResult<Boolean>>
    fun changeCongratsShown(): Flow<DomainResult<Unit>>
    fun getLocale(): Flow<DomainResult<String?>>
    suspend fun getLocaleTag(): String?
    fun setLocale(locale: String): Flow<DomainResult<Unit>>
}
