package siarhei.luskanau.pixabayeye.ui.di

import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.ui.app.AppViewModel

val uiModule = module {
    single { AppViewModel(pixabayApiService = get()) }
}
