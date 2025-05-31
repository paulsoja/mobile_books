package com.spasinnya.mentoring.domain.repository

import com.spasinnya.mentoring.domain.models.AuthToken
import com.spasinnya.mentoring.domain.models.EmailPassword
import com.spasinnya.mentoring.domain.models.OtpCode

typealias LoginRepository = suspend (EmailPassword) -> AuthToken?
typealias RegisterRepository = suspend (EmailPassword) -> Boolean
typealias OtpRepository = suspend (EmailPassword, OtpCode) -> Boolean