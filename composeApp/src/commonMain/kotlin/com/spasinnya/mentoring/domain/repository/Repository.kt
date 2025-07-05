package com.spasinnya.mentoring.domain.repository

import com.spasinnya.mentoring.domain.models.AuthToken
import com.spasinnya.mentoring.domain.models.Credentials
import com.spasinnya.mentoring.domain.models.OtpCode

typealias LoginRepository = suspend (Credentials) -> AuthToken?
typealias RegisterRepository = suspend (Credentials) -> Boolean
typealias OtpRepository = suspend (Credentials, OtpCode) -> Boolean