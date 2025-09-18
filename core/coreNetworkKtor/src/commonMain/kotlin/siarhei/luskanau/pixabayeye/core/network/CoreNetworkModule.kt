package siarhei.luskanau.pixabayeye.core.network

import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.core.network.api.PixabayApiService
import siarhei.luskanau.pixabayeye.core.network.ktor.KtorPixabayApiService
import siarhei.luskanau.pixabayeye.core.network.ktor.PixabayApiClient

val coreNetworkModule = module {
    single { PixabayApiClient(prefService = get()) }
    single<PixabayApiService> { KtorPixabayApiService(client = get()) }
}
