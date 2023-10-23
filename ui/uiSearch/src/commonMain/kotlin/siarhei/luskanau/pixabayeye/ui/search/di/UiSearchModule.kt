package siarhei.luskanau.pixabayeye.ui.search.di

import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.ui.search.SearchVewModel

val uiSearchModule =
    module {
        single {
            SearchVewModel(pixabayApiService = get())
        }
    }
