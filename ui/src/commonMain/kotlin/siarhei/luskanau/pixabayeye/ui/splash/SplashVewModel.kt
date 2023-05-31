package siarhei.luskanau.pixabayeye.ui.splash

import siarhei.luskanau.pixabayeye.network.NetworkResult
import siarhei.luskanau.pixabayeye.network.PixabayApiService

class SplashVewModel(private val pixabayApiService: PixabayApiService) {
    suspend fun isApiKeyOk(): NetworkResult<Boolean> =
        pixabayApiService.isApiKeyOk()
}
