package siarhei.luskanau.pixabayeye.ui.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import siarhei.luskanau.pixabayeye.network.HitModel
import siarhei.luskanau.pixabayeye.network.NetworkResult
import siarhei.luskanau.pixabayeye.ui.AppTheme
import siarhei.luskanau.pixabayeye.ui.details.DetailsView
import siarhei.luskanau.pixabayeye.ui.login.LoginView
import siarhei.luskanau.pixabayeye.ui.search.SearchView
import siarhei.luskanau.pixabayeye.ui.splash.SplashView

@Composable
fun App(appViewModel: AppViewModel) = AppTheme {
    val appViewState = remember { mutableStateOf<AppViewState>(AppViewState.Splash) }
    when (val viewState = appViewState.value) {
        is AppViewState.Details -> DetailsView(hitModel = viewState.hitModel)

        AppViewState.Login -> {
            val loginVewModel = appViewModel.createLoginVewModel {
                appViewState.value = AppViewState.Search
            }
            LoginView(
                loginVewState = loginVewModel.getLoginVewState(),
                onUpdateClick = { apiKey -> loginVewModel.onUpdateClick(apiKey) },
            )
        }

        AppViewState.Search -> SearchView(
            searchVewStateFlow = appViewModel.searchVewModel.getSearchVewStateFlow(),
            onUpdateSearchTerm = { searchTerm ->
                appViewModel.searchVewModel.onUpdateSearchTerm(searchTerm = searchTerm)
            },
            onImageClicked = { hitModel ->
                appViewState.value = AppViewState.Details(hitModel = hitModel)
            },
        )

        AppViewState.Splash -> SplashView(
            onSplashComplete = {
                when (appViewModel.splashVewModel.isApiKeyOk()) {
                    is NetworkResult.Failure -> appViewState.value = AppViewState.Login
                    is NetworkResult.Success -> appViewState.value = AppViewState.Search
                }
            },
        )
    }
}

internal sealed interface AppViewState {
    object Splash : AppViewState
    object Search : AppViewState
    object Login : AppViewState
    data class Details(val hitModel: HitModel) : AppViewState
}
