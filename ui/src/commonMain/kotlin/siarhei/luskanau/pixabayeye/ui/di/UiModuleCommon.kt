package siarhei.luskanau.pixabayeye.ui.di

import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.ui.AppViewModel
import siarhei.luskanau.pixabayeye.ui.AppViewModelImpl

val uiModule = module {
    single<AppViewModel> { AppViewModelImpl() }
}

// expect val uiPlatformModule: Module
