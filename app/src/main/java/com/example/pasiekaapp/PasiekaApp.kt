package com.example.pasiekaapp

import RegisterViewModel
import UserRepositoryImpl
import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.scope.get
import org.koin.dsl.module

class PasiekaApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@PasiekaApp)
            modules(appModule)
        }
    }

    private val appModule = module {
        single<SplashScreenUseCase> { SplashScreenUseCaseImpl() }
        single { FirebaseAuth.getInstance() }
        single { FirebaseFirestore.getInstance() }
        single { LoginUseCase(get()) }

        //repo
        single<UserRepository> { UserRepositoryImpl(get()) }
        //usecase
        single { RegisterUseCase(get()) }

        //viewModel
        viewModel { RegisterViewModel(get()) }
        viewModel{ SplashScreenViewModel(get()) }
        viewModel { LoginViewModel(get()) }
    }
}