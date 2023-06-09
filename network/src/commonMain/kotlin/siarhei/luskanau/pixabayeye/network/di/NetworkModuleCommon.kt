package siarhei.luskanau.pixabayeye.network.di

import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.network.PixabayApiService
import siarhei.luskanau.pixabayeye.network.ktor.KtorPixabayApiService
import siarhei.luskanau.pixabayeye.network.ktor.PixabayApiClient

val networkModule = module {

    single {
        PixabayApiClient(
            prefService = get(),
            dispatcherSet = get(),
        )
    }

    single<PixabayApiService> {
        KtorPixabayApiService(
            client = get(),
        )
    }
}
