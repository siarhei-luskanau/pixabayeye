package siarhei.luskanau.pixabayeye.core.network

import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.core.network.api.PixabayApiService
import siarhei.luskanau.pixabayeye.core.network.stub.StubPixabayApiService

val coreNetworkModule = module {
    single<PixabayApiService> { StubPixabayApiService() }
}
