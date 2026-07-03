package com.spasinnya.mentoring.domain.repository

import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getProfile(): Flow<DomainResult<UserProfile>>
    fun updateProfile(firstName: String?, lastName: String?): Flow<DomainResult<UserProfile>>
}
