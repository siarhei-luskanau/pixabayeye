package siarhei.luskanau.pixabayeye.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
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
import coil3.ImageLoader
import coil3.addLastModifiedToFileCacheKey
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor.KtorNetworkFetcherFactory
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import siarhei.luskanau.pixabayeye.core.common.DispatcherSet
import siarhei.luskanau.pixabayeye.core.network.HitModel
import siarhei.luskanau.pixabayeye.core.network.NetworkResult
import siarhei.luskanau.pixabayeye.ui.common.resources.Res
import siarhei.luskanau.pixabayeye.ui.common.resources.back_button
import siarhei.luskanau.pixabayeye.ui.common.resources.screen_name_login
import siarhei.luskanau.pixabayeye.ui.common.resources.screen_name_search
import siarhei.luskanau.pixabayeye.ui.common.resources.screen_name_splash
import siarhei.luskanau.pixabayeye.ui.details.DetailsComposable
import siarhei.luskanau.pixabayeye.ui.login.LoginComposable
import siarhei.luskanau.pixabayeye.ui.search.SearchComposable
import siarhei.luskanau.pixabayeye.ui.splash.SplashComposable

@Composable
fun App(appViewModel: AppViewModel, dispatcherSet: DispatcherSet) = AppTheme {
    @OptIn(ExperimentalCoilApi::class)
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .dispatcher(dispatcherSet.ioDispatcher())
            .components {
                add(KtorNetworkFetcherFactory())
            }
            .addLastModifiedToFileCacheKey(false)
            .build()
    }

    val appViewState = remember { mutableStateOf<AppViewState>(AppViewState.Splash) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(appViewState.value.title)) },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                navigationIcon = {
                    if (appViewState.value is AppViewState.Details) {
                        IconButton(onClick = { appViewState.value = AppViewState.Search }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(Res.string.back_button)
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                content = {
                    when (appViewState.value) {
                        is AppViewState.Details,
                        AppViewState.Search -> {
                            IconButton(
                                onClick = { appViewState.value = AppViewState.Search }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = null
                                )
                            }
                        }

                        AppViewState.Login,
                        AppViewState.Splash -> Unit
                    }

                    when (appViewState.value) {
                        is AppViewState.Details,
                        AppViewState.Login,
                        AppViewState.Search -> {
                            IconButton(
                                onClick = { appViewState.value = AppViewState.Login }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AccountBox,
                                    contentDescription = null
                                )
                            }
                        }

                        AppViewState.Splash -> Unit
                    }
                }
            )
        }
    ) { contentPadding ->
        when (val viewState = appViewState.value) {
            is AppViewState.Details -> DetailsComposable(
                hitModel = viewState.hitModel,
                modifier = Modifier.padding(contentPadding)
            )

            AppViewState.Login -> {
                val loginVewModel = appViewModel.createLoginVewModel {
                    appViewState.value = AppViewState.Search
                }
                LoginComposable(
                    loginVewState = loginVewModel.getLoginVewState(),
                    modifier = Modifier.padding(contentPadding),
                    onInit = { loginVewModel.onInit() },
                    onUpdateClick = { apiKey -> loginVewModel.onUpdateClick(apiKey) },
                    onCheckClick = { loginVewModel.onCheckClick() }
                )
            }

            AppViewState.Search -> SearchComposable(
                searchVewStateFlow = appViewModel.searchVewModel.getSearchVewStateFlow(),
                onUpdateSearchTerm = { searchTerm ->
                    appViewModel.searchVewModel.onUpdateSearchTerm(searchTerm = searchTerm)
                },
                onImageClicked = { hitModel ->
                    appViewState.value = AppViewState.Details(hitModel = hitModel)
                },
                modifier = Modifier.padding(contentPadding)
            )

            AppViewState.Splash -> SplashComposable(
                onSplashComplete = {
                    when (appViewModel.splashVewModel.isApiKeyOk()) {
                        is NetworkResult.Failure -> appViewState.value = AppViewState.Login
                        is NetworkResult.Success -> appViewState.value = AppViewState.Search
                    }
                },
                modifier = Modifier.padding(contentPadding)
            )
        }
    }
}

internal sealed class AppViewState(val route: String, val title: StringResource) {
    data object Splash : AppViewState(
        route = "splash",
        title = Res.string.screen_name_splash
    )

    data object Search : AppViewState(
        route = "search",
        title = Res.string.screen_name_search
    )

    data object Login : AppViewState(
        route = "login",
        title = Res.string.screen_name_login
    )

    data class Details(val hitModel: HitModel) :
        AppViewState(
            route = "details",
            title = Res.string.screen_name_search
        )
}
