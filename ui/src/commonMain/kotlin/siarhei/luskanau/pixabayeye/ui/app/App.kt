package siarhei.luskanau.pixabayeye.ui.app

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    @OptIn(ExperimentalMaterial3Api::class)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (appViewState.value) {
                            is AppViewState.Details,
                            AppViewState.Search,
                            -> "PixabayEye - Search"
                            AppViewState.Login -> "PixabayEye - API key"
                            AppViewState.Splash -> "PixabayEye"
                        },
                    )
                },
                navigationIcon = {
                    if (appViewState.value is AppViewState.Details) {
                        IconButton(
                            onClick = { appViewState.value = AppViewState.Search },
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                tint = Color.White,
                                contentDescription = null,
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                ),
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                content = {
                    when (appViewState.value) {
                        is AppViewState.Details,
                        AppViewState.Search,
                        -> {
                            IconButton(
                                onClick = { appViewState.value = AppViewState.Search },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = null,
                                )
                            }
                        }

                        AppViewState.Login,
                        AppViewState.Splash,
                        -> Unit
                    }

                    when (appViewState.value) {
                        is AppViewState.Details,
                        AppViewState.Login,
                        AppViewState.Search,
                        -> {
                            IconButton(
                                onClick = { appViewState.value = AppViewState.Login },
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AccountBox,
                                    contentDescription = null,
                                )
                            }
                        }

                        AppViewState.Splash -> Unit
                    }
                },
            )
        },
    ) { contentPadding ->
        when (val viewState = appViewState.value) {
            is AppViewState.Details -> DetailsView(
                hitModel = viewState.hitModel,
                modifier = Modifier.padding(contentPadding),
            )

            AppViewState.Login -> {
                val loginVewModel = appViewModel.createLoginVewModel {
                    appViewState.value = AppViewState.Search
                }
                LoginView(
                    loginVewState = loginVewModel.getLoginVewState(),
                    modifier = Modifier.padding(contentPadding),
                    onInit = { loginVewModel.onInit() },
                    onUpdateClick = { apiKey -> loginVewModel.onUpdateClick(apiKey) },
                    onCheckClick = { loginVewModel.onCheckClick() },
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
                modifier = Modifier.padding(contentPadding),
            )

            AppViewState.Splash -> SplashView(
                onSplashComplete = {
                    when (appViewModel.splashVewModel.isApiKeyOk()) {
                        is NetworkResult.Failure -> appViewState.value = AppViewState.Login
                        is NetworkResult.Success -> appViewState.value = AppViewState.Search
                    }
                },
                modifier = Modifier.padding(contentPadding),
            )
        }
    }
}

internal sealed interface AppViewState {
    object Splash : AppViewState
    object Search : AppViewState
    object Login : AppViewState
    data class Details(val hitModel: HitModel) : AppViewState
}
