package siarhei.luskanau.pixabayeye.ui.di

import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.ui.app.AppViewModel
import siarhei.luskanau.pixabayeye.ui.app.AppViewModelImpl

val uiModule = module {
    single<AppViewModel> {
        AppViewModelImpl(
            pixabayApiService = get(),
        )
    }
}
