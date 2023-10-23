package siarhei.luskanau.pixabayeye.ui.splash

import kotlinx.coroutines.flow.firstOrNull
import siarhei.luskanau.pixabayeye.core.network.NetworkResult
import siarhei.luskanau.pixabayeye.core.network.PixabayApiService
import siarhei.luskanau.pixabayeye.core.pref.PrefService

class SplashVewModel(
    private val pixabayApiService: PixabayApiService,
    private val prefService: PrefService,
) {
    suspend fun isApiKeyOk(): NetworkResult<Boolean> =
        pixabayApiService.isApiKeyOk(
            apiKey = prefService.getPixabayApiKey().firstOrNull(),
        )
}
