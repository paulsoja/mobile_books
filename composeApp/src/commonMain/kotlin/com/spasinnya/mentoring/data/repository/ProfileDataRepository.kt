package com.spasinnya.mentoring.data.repository

import com.spasinnya.mentoring.data.mapper.toDomain
import com.spasinnya.mentoring.data.mapper.toDomainError
import com.spasinnya.mentoring.data.model.UpdateProfileApiRequest
import com.spasinnya.mentoring.data.model.UserProfileApiResponse
import com.spasinnya.mentoring.data.net.getFlow
import com.spasinnya.mentoring.data.net.patchFlow
import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.UserProfile
import com.spasinnya.mentoring.domain.repository.ProfileRepository
import com.spasinnya.mentoring.presentation.base.map
import com.spasinnya.mentoring.presentation.base.mapErrors
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProfileDataRepository(
    private val http: HttpClient,
) : ProfileRepository {

    override fun getProfile(): Flow<DomainResult<UserProfile>> =
        http.getFlow<UserProfileApiResponse>(path = "me")
            .map { result ->
                result
                    .map(UserProfileApiResponse::toDomain)
                    .mapErrors { it.toDomainError() }
            }

    override fun updateProfile(firstName: String?, lastName: String?): Flow<DomainResult<UserProfile>> =
        http.patchFlow<UpdateProfileApiRequest, UserProfileApiResponse>(
            path = "me",
            body = UpdateProfileApiRequest(firstName = firstName, lastName = lastName)
        ).map { result ->
            result
                .map(UserProfileApiResponse::toDomain)
                .mapErrors { it.toDomainError() }
        }
}
