package siarhei.luskanau.pixabayeye.ui.login

import kotlinx.coroutines.flow.firstOrNull
import siarhei.luskanau.pixabayeye.core.DispatcherSet
import siarhei.luskanau.pixabayeye.network.NetworkResult
import siarhei.luskanau.pixabayeye.network.PixabayApiService
import siarhei.luskanau.pixabayeye.pref.PrefService

class LoginVewModel(
    private val prefService: PrefService,
    private val pixabayApiService: PixabayApiService,
    private val dispatcherSet: DispatcherSet,
    private val onLoginComplete: () -> Unit,
) {

    fun getLoginVewState(): LoginVewState =
        dispatcherSet.runBlocking(dispatcherSet.ioDispatcher()) {
            LoginVewState(
                apiKey = prefService.getPixabayApiKey().firstOrNull(),
            )
        }

    suspend fun onUpdateClick(apiKey: String?) {
        when (pixabayApiService.isApiKeyOk(apiKey = apiKey)) {
            is NetworkResult.Failure -> Unit // do nothing
            is NetworkResult.Success -> {
                prefService.setPixabayApiKey(apiKey)
                onLoginComplete.invoke()
            }
        }
    }
}
