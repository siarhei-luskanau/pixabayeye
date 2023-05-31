package siarhei.luskanau.pixabayeye.ui.login

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import siarhei.luskanau.pixabayeye.pref.PrefService

class LoginVewModel(private val prefService: PrefService) {

    fun getLoginVewStateFlow(): Flow<LoginVewState> =
        prefService.getPixabayApiKey().map { LoginVewState(apiKey = it.orEmpty()) }

    suspend fun updateApiKey(apiKey: String?) {
        prefService.setPixabayApiKey(apiKey)
    }
}
