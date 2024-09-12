package siarhei.luskanau.pixabayeye.ui.splash

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import org.koin.core.annotation.Provided
import siarhei.luskanau.pixabayeye.core.common.DispatcherSet
import siarhei.luskanau.pixabayeye.core.network.NetworkResult
import siarhei.luskanau.pixabayeye.core.network.PixabayApiService
import siarhei.luskanau.pixabayeye.core.pref.PrefService
import siarhei.luskanau.pixabayeye.ui.splash.SplashNavigationCallback.LoginState

@Factory
class SplashViewModelImpl(
    @Provided private val pixabayApiService: PixabayApiService,
    @Provided private val prefService: PrefService,
    @Provided private val dispatcherSet: DispatcherSet,
    @InjectedParam private val splashNavigationCallback: SplashNavigationCallback
) : SplashViewModel() {

    override fun onSplashComplete() {
        viewModelScope.launch {
            val isApiKeyOk = withContext(dispatcherSet.ioDispatcher()) {
                val apiKey = prefService.getPixabayApiKey().firstOrNull()
                pixabayApiService.isApiKeyOk(apiKey = apiKey)
            }
            splashNavigationCallback.onSplashScreenCompleted(
                loginState = when (isApiKeyOk) {
                    is NetworkResult.Failure -> LoginState.NoLogin
                    is NetworkResult.Success -> LoginState.HasLogin
                }
            )
        }
    }
}
