package com.spasinnya.mentoring.domain.usecase.profile

import com.spasinnya.mentoring.domain.model.DomainResult
import com.spasinnya.mentoring.domain.model.UserProfile
import com.spasinnya.mentoring.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow

class GetProfileUseCase(
    private val profileRepository: ProfileRepository
) {
    operator fun invoke(): Flow<DomainResult<UserProfile>> =
        profileRepository.getProfile()
}
