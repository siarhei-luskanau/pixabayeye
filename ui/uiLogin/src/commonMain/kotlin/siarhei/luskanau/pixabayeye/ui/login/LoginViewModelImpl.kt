package siarhei.luskanau.pixabayeye.ui.login

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Provided
import siarhei.luskanau.pixabayeye.core.network.NetworkResult
import siarhei.luskanau.pixabayeye.core.network.PixabayApiService
import siarhei.luskanau.pixabayeye.core.pref.PrefService

@Factory
class LoginViewModelImpl(
    @Provided private val prefService: PrefService,
    @Provided private val pixabayApiService: PixabayApiService,
    @InjectedParam private val loginNavigationCallback: LoginNavigationCallback
) : LoginViewModel() {
    private val apiKeyFlow = MutableStateFlow<String?>("")

    override fun getLoginViewState(): LoginViewState = LoginViewState(apiKeyFlow = apiKeyFlow)

    override fun onInit() {
        viewModelScope.launch {
            apiKeyFlow.emit(
                prefService.getPixabayApiKey().firstOrNull()
            )
        }
    }

    override fun onUpdateClick(apiKey: String?) {
        viewModelScope.launch {
            apiKeyFlow.emit(apiKey)
        }
    }

    override fun onCheckClick() {
        viewModelScope.launch {
            val apiKey = apiKeyFlow.firstOrNull()
            when (pixabayApiService.isApiKeyOk(apiKey = apiKey)) {
                is NetworkResult.Failure -> Unit // do nothing
                is NetworkResult.Success -> {
                    prefService.setPixabayApiKey(apiKey)
                    loginNavigationCallback.onLoginScreenCompleted()
                }
            }
        }
    }
}
