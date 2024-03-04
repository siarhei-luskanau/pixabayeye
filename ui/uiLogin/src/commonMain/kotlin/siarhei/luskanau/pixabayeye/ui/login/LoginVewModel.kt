package siarhei.luskanau.pixabayeye.ui.login

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import siarhei.luskanau.pixabayeye.core.network.NetworkResult
import siarhei.luskanau.pixabayeye.core.network.PixabayApiService
import siarhei.luskanau.pixabayeye.core.pref.PrefService

class LoginVewModel(
    private val prefService: PrefService,
    private val pixabayApiService: PixabayApiService,
    private val onLoginComplete: () -> Unit
) {
    private val apiKeyFlow = MutableStateFlow<String?>("")

    fun getLoginVewState(): LoginVewState = LoginVewState(apiKeyFlow = apiKeyFlow)

    suspend fun onInit() {
        apiKeyFlow.emit(
            prefService.getPixabayApiKey().firstOrNull()
        )
    }

    suspend fun onUpdateClick(apiKey: String?) {
        apiKeyFlow.emit(apiKey)
    }

    suspend fun onCheckClick() {
        val apiKey = apiKeyFlow.firstOrNull()
        when (pixabayApiService.isApiKeyOk(apiKey = apiKey)) {
            is NetworkResult.Failure -> Unit // do nothing
            is NetworkResult.Success -> {
                prefService.setPixabayApiKey(apiKey)
                onLoginComplete.invoke()
            }
        }
    }
}
