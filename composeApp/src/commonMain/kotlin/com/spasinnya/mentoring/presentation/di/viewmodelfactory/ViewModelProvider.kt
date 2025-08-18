package com.spasinnya.mentoring.presentation.di.viewmodelfactory

import com.spasinnya.mentoring.presentation.di.UseCaseProvider.loginUseCase
import com.spasinnya.mentoring.presentation.di.UseCaseProvider.registerUseCase
import com.spasinnya.mentoring.presentation.screens.authflow.login.LoginViewModel
import com.spasinnya.mentoring.presentation.screens.authflow.newpassword.NewPasswordViewModel
import com.spasinnya.mentoring.presentation.screens.authflow.otp.OtpViewModel
import com.spasinnya.mentoring.presentation.screens.authflow.register.RegisterViewModel
import com.spasinnya.mentoring.presentation.screens.authflow.resetpassword.ResetPasswordViewModel
import com.spasinnya.mentoring.presentation.screens.homeflow.home.HomeViewModel
import com.spasinnya.mentoring.presentation.screens.homeflow.lessons.LessonsViewModel
import com.spasinnya.mentoring.presentation.screens.homeflow.weeks.WeeksViewModel

// Home flow
val createLessonsViewModel = create { LessonsViewModel() }
val createWeeksViewModel = create { WeeksViewModel() }
val createHomeViewModel = create { HomeViewModel() }

// Auth flow
val createLoginViewModel = create { LoginViewModel(loginUseCase = loginUseCase) }
val createResetPasswordViewModel = create { ResetPasswordViewModel() }
val createRegisterViewModel = create { RegisterViewModel(registerUseCase = registerUseCase) }
val createOtpViewModel = create { OtpViewModel() }
val createNewPasswordViewModel = create { NewPasswordViewModel() }