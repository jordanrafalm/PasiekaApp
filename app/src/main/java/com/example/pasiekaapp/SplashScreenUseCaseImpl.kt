package com.example.pasiekaapp

import kotlinx.coroutines.delay
class SplashScreenUseCaseImpl: SplashScreenUseCase {

    override suspend fun delay() {
        delay(4000)
    }
}