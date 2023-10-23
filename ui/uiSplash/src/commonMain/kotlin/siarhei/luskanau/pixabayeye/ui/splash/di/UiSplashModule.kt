package siarhei.luskanau.pixabayeye.ui.splash.di

import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.ui.splash.SplashVewModel

val uiSplashModule =
    module {
        single {
            SplashVewModel(
                pixabayApiService = get(),
                prefService = get(),
            )
        }
    }
