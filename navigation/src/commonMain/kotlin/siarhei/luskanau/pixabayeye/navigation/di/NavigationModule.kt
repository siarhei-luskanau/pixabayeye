package siarhei.luskanau.pixabayeye.navigation.di

import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.navigation.AppViewModel
import siarhei.luskanau.pixabayeye.ui.login.di.uiLoginModule
import siarhei.luskanau.pixabayeye.ui.search.di.uiSearchModule
import siarhei.luskanau.pixabayeye.ui.splash.di.uiSplashModule

val uiModules =
    listOf(
        uiLoginModule,
        uiSearchModule,
        uiSplashModule
    )

val navigationModule =
    module {
        single {
            AppViewModel(
                loginVewModel = { onLoginComplete ->
                    get(parameters = { parametersOf(onLoginComplete) })
                },
                searchVewModel = get(),
                splashVewModel = get()
            )
        }
    }
