package siarhei.luskanau.pixabayeye.ui.di

import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.ui.app.AppViewModel
import siarhei.luskanau.pixabayeye.ui.search.SearchVewModel

val uiModule = module {
    single {
        AppViewModel(
            pixabayApiService = get(),
            prefService = get(),
            searchVewModel = get(),
        )
    }
    single {
        SearchVewModel(
            pixabayApiService = get(),
        )
    }
}
