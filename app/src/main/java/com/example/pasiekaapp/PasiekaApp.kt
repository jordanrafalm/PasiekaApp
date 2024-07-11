package com.example.pasiekaapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
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

        viewModel{ SplashScreenViewModel(get()) }
    }
}