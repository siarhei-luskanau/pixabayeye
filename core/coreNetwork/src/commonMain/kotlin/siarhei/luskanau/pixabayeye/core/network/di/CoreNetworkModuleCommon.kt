package siarhei.luskanau.pixabayeye.core.network.di

import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.core.network.PixabayApiService
import siarhei.luskanau.pixabayeye.core.network.ktor.KtorPixabayApiService
import siarhei.luskanau.pixabayeye.core.network.ktor.PixabayApiClient

val coreNetworkModule =
    module {

        single {
            PixabayApiClient(
                prefService = get(),
                dispatcherSet = get()
            )
        }

        single<PixabayApiService> {
            KtorPixabayApiService(
                client = get()
            )
        }
    }
