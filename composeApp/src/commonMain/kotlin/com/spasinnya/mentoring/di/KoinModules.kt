package com.spasinnya.mentoring.di

import com.spasinnya.mentoring.data.net.createHttpClient
import com.spasinnya.mentoring.data.parser.HomeworkMarkdownParser
import com.spasinnya.mentoring.data.parser.MentorshipMarkdownParser
import com.spasinnya.mentoring.data.repository.AuthDataRepository
import com.spasinnya.mentoring.data.repository.BooksDataRepository
import com.spasinnya.mentoring.data.repository.PrefDataRepository
import com.spasinnya.mentoring.data.repository.ProfileDataRepository
import com.spasinnya.mentoring.data.storage.BookFileDataSource
import com.spasinnya.mentoring.domain.repository.AuthRepository
import com.spasinnya.mentoring.domain.repository.BooksRepository
import com.spasinnya.mentoring.domain.repository.PrefRepository
import com.spasinnya.mentoring.domain.repository.ProfileRepository
import com.spasinnya.mentoring.domain.usecase.auth.ChangeCongratsShownStatusUseCase
import com.spasinnya.mentoring.domain.usecase.auth.CheckAuthStepsUseCase
import com.spasinnya.mentoring.domain.usecase.auth.ConfirmOtpCodeUseCase
import com.spasinnya.mentoring.domain.usecase.auth.LoginUseCase
import com.spasinnya.mentoring.domain.usecase.auth.LogoutUseCase
import com.spasinnya.mentoring.domain.usecase.auth.RegisterUseCase
import com.spasinnya.mentoring.domain.usecase.auth.RequestOtpCodeUseCase
import com.spasinnya.mentoring.domain.usecase.auth.ResetPasswordUseCase
import com.spasinnya.mentoring.domain.usecase.books.GetBooksUseCase
import com.spasinnya.mentoring.domain.usecase.books.GetHomeworkAnswersUseCase
import com.spasinnya.mentoring.domain.usecase.books.GetWeeksUseCase
import com.spasinnya.mentoring.domain.usecase.books.ObserveBookUseCase
import com.spasinnya.mentoring.domain.usecase.books.ObserveHomeworkUseCase
import com.spasinnya.mentoring.domain.usecase.books.PurchaseBookUseCase
import com.spasinnya.mentoring.domain.usecase.books.SaveHomeworkAnswersUseCase
import com.spasinnya.mentoring.domain.usecase.profile.GetProfileUseCase
import com.spasinnya.mentoring.domain.usecase.profile.UpdateProfileUseCase
import com.spasinnya.mentoring.domain.usecase.settings.GetAppLocaleUseCase
import com.spasinnya.mentoring.domain.usecase.settings.SetAppLocaleUseCase
import com.spasinnya.mentoring.presentation.app.SessionViewModel
import com.spasinnya.mentoring.presentation.screens.authflow.congrat.CongratViewModel
import com.spasinnya.mentoring.presentation.screens.authflow.login.LoginViewModel
import com.spasinnya.mentoring.presentation.screens.authflow.newpassword.NewPasswordViewModel
import com.spasinnya.mentoring.presentation.screens.authflow.otp.OtpViewModel
import com.spasinnya.mentoring.presentation.screens.authflow.register.RegisterViewModel
import com.spasinnya.mentoring.presentation.screens.authflow.resetpassword.ResetPasswordViewModel
import com.spasinnya.mentoring.presentation.screens.homeflow.home.HomeViewModel
import com.spasinnya.mentoring.presentation.screens.homeflow.lessons.LessonsViewModel
import com.spasinnya.mentoring.presentation.screens.homeflow.lessons.PracticalWorkViewModel
import com.spasinnya.mentoring.presentation.screens.homeflow.profile.ProfileViewModel
import com.spasinnya.mentoring.presentation.screens.homeflow.weeks.WeeksViewModel
import okio.FileSystem
import okio.SYSTEM
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    single { FileSystem.SYSTEM }
    singleOf(::BookFileDataSource)
    singleOf(::MentorshipMarkdownParser)
    singleOf(::HomeworkMarkdownParser)
    single {
        createHttpClient(
            tokenStore = get(),
            readLanguageTag = { get<PrefRepository>().getLocaleTag() },
            defaultLang = "en"
        )
    }
}

val repositoryModule = module {
    singleOf(::AuthDataRepository) bind AuthRepository::class
    singleOf(::BooksDataRepository) bind BooksRepository::class
    singleOf(::PrefDataRepository) bind PrefRepository::class
    singleOf(::ProfileDataRepository) bind ProfileRepository::class
}

val useCaseModule = module {
    factoryOf(::LoginUseCase)
    factoryOf(::RegisterUseCase)
    factoryOf(::ConfirmOtpCodeUseCase)
    factoryOf(::RequestOtpCodeUseCase)
    factoryOf(::ResetPasswordUseCase)
    factoryOf(::LogoutUseCase)
    factoryOf(::CheckAuthStepsUseCase)
    factoryOf(::ChangeCongratsShownStatusUseCase)
    factoryOf(::GetBooksUseCase)
    factoryOf(::PurchaseBookUseCase)
    factoryOf(::GetWeeksUseCase)
    factoryOf(::ObserveBookUseCase)
    factoryOf(::ObserveHomeworkUseCase)
    factoryOf(::GetHomeworkAnswersUseCase)
    factoryOf(::SaveHomeworkAnswersUseCase)
    factoryOf(::GetProfileUseCase)
    factoryOf(::UpdateProfileUseCase)
    factoryOf(::GetAppLocaleUseCase)
    factoryOf(::SetAppLocaleUseCase)
}

val viewModelModule = module {
    viewModelOf(::SessionViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)
    viewModelOf(::OtpViewModel)
    viewModelOf(::CongratViewModel)
    viewModelOf(::NewPasswordViewModel)
    viewModelOf(::ResetPasswordViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::ProfileViewModel)
    viewModel { params -> WeeksViewModel(bookId = params.get(), weeksUseCase = get()) }
    viewModel { params ->
        LessonsViewModel(
            bookId = params.get(),
            weekNumber = params.get(),
            importBookFromMarkdownUseCase = get(),
            observeHomeworkUseCase = get()
        )
    }
    viewModel { params ->
        PracticalWorkViewModel(
            bookId = params.get(0),
            weekNumber = params.get(1),
            lessonNumber = params.get(2),
            observeHomeworkUseCase = get(),
            getHomeworkAnswersUseCase = get(),
            saveHomeworkAnswersUseCase = get()
        )
    }
}
