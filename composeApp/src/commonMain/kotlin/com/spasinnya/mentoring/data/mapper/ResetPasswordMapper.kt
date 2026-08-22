package com.spasinnya.mentoring.data.mapper

import com.spasinnya.mentoring.data.model.OtpPurposeApiRequest
import com.spasinnya.mentoring.data.model.ResetPasswordApiRequest
import com.spasinnya.mentoring.domain.enums.OtpPurpose
import com.spasinnya.mentoring.domain.model.ResetPasswordCredentials

fun ResetPasswordCredentials.toData() = ResetPasswordApiRequest(
    email = email.value,
    code = otp.value,
    newPassword = password.value
)

fun OtpPurpose.toData(): OtpPurposeApiRequest =
    when (this) {
        OtpPurpose.LOGIN -> OtpPurposeApiRequest.LOGIN
        OtpPurpose.PASSWORD_RESET -> OtpPurposeApiRequest.PASSWORD_RESET
    }
