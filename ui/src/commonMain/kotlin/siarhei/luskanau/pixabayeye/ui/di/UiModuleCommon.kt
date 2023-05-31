package siarhei.luskanau.pixabayeye.ui.di

import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.ui.app.AppViewModel
import siarhei.luskanau.pixabayeye.ui.login.LoginVewModel
import siarhei.luskanau.pixabayeye.ui.search.SearchVewModel
import siarhei.luskanau.pixabayeye.ui.splash.SplashVewModel

val uiModule = module {
    single {
        AppViewModel(
            loginVewModel = get(),
            searchVewModel = get(),
            splashVewModel = get(),
        )
    }
    single {
        LoginVewModel(prefService = get())
    }
    single {
        SearchVewModel(pixabayApiService = get())
    }
    single {
        SplashVewModel(pixabayApiService = get())
    }
}
