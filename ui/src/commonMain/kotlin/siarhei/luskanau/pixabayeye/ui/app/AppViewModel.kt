package siarhei.luskanau.pixabayeye.ui.app

import siarhei.luskanau.pixabayeye.network.NetworkResult
import siarhei.luskanau.pixabayeye.network.PixabayApiService

class AppViewModel(
    private val pixabayApiService: PixabayApiService,
) {
    suspend fun isApiKeyOk(): NetworkResult<Boolean> =
        pixabayApiService.isApiKeyOk()
}
