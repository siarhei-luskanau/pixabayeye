package siarhei.luskanau.pixabayeye.ui.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.emptyFlow
import siarhei.luskanau.pixabayeye.network.NetworkResult
import siarhei.luskanau.pixabayeye.ui.AppTheme
import siarhei.luskanau.pixabayeye.ui.details.DetailsView
import siarhei.luskanau.pixabayeye.ui.login.LoginView
import siarhei.luskanau.pixabayeye.ui.search.SearchView
import siarhei.luskanau.pixabayeye.ui.splash.SplashView
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
fun App(appViewModel: AppViewModel) = AppTheme {
    val appViewState = remember { mutableStateOf<AppViewState>(AppViewState.Splash) }
    when (appViewState.value) {
        AppViewState.Details -> DetailsView(detailsVewState = emptyFlow())

        AppViewState.Login -> LoginView(
            loginVewStateFlow = appViewModel.getLoginVewState(),
            onTextUpdated = { apiKey -> appViewModel.updateApiKey(apiKey) },
            onClick = { appViewState.value = AppViewState.Search },
        )

        AppViewState.Search -> SearchView(
            pager = appViewModel.searchVewModel.getPager(""),
        )

        AppViewState.Splash -> SplashView(
            splashVewState = emptyFlow(),
            onSplashComplete = {
                when (appViewModel.isApiKeyOk()) {
                    is NetworkResult.Failure -> appViewState.value = AppViewState.Login
                    is NetworkResult.Success -> appViewState.value = AppViewState.Search
                }
                delay(duration = 2.toDuration(DurationUnit.SECONDS))
            },
        )
    }
}

internal sealed interface AppViewState {
    object Splash : AppViewState
    object Search : AppViewState
    object Login : AppViewState
    object Details : AppViewState
}
