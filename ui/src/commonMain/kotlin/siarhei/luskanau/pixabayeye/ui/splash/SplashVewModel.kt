package siarhei.luskanau.pixabayeye.ui.splash

import kotlinx.coroutines.flow.firstOrNull
import siarhei.luskanau.pixabayeye.network.NetworkResult
import siarhei.luskanau.pixabayeye.network.PixabayApiService
import siarhei.luskanau.pixabayeye.pref.PrefService

class SplashVewModel(
    private val pixabayApiService: PixabayApiService,
    private val prefService: PrefService,
) {
    suspend fun isApiKeyOk(): NetworkResult<Boolean> =
        pixabayApiService.isApiKeyOk(
            apiKey = prefService.getPixabayApiKey().firstOrNull(),
        )
}
