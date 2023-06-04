package siarhei.luskanau.pixabayeye.ui.di

import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.ui.app.AppViewModel
import siarhei.luskanau.pixabayeye.ui.login.LoginVewModel
import siarhei.luskanau.pixabayeye.ui.search.SearchVewModel
import siarhei.luskanau.pixabayeye.ui.splash.SplashVewModel

val uiModule = module {
    single {
        AppViewModel(
            loginVewModel = { onLoginComplete -> get(parameters = { parametersOf(onLoginComplete) }) },
            searchVewModel = get(),
            splashVewModel = get(),
        )
    }
    single {
        val onLoginComplete: () -> Unit = it.get()
        LoginVewModel(
            prefService = get(),
            pixabayApiService = get(),
            onLoginComplete = onLoginComplete,
        )
    }
    single {
        SearchVewModel(pixabayApiService = get())
    }
    single {
        SplashVewModel(
            pixabayApiService = get(),
            prefService = get(),
        )
    }
}
