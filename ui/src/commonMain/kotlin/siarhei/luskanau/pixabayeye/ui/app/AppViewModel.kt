package siarhei.luskanau.pixabayeye.ui.app

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import siarhei.luskanau.pixabayeye.network.NetworkResult
import siarhei.luskanau.pixabayeye.network.PixabayApiService
import siarhei.luskanau.pixabayeye.pref.PrefService
import siarhei.luskanau.pixabayeye.ui.login.LoginVewState
import siarhei.luskanau.pixabayeye.ui.search.SearchVewModel

class AppViewModel(
    private val pixabayApiService: PixabayApiService,
    private val prefService: PrefService,
    val searchVewModel: SearchVewModel,
) {
    fun getLoginVewState(): Flow<LoginVewState> =
        prefService.getPixabayApiKey().map { LoginVewState(apiKey = it.orEmpty()) }

    suspend fun updateApiKey(apiKey: String?) {
        prefService.setPixabayApiKey(apiKey)
    }

    suspend fun isApiKeyOk(): NetworkResult<Boolean> =
        pixabayApiService.isApiKeyOk()
}
