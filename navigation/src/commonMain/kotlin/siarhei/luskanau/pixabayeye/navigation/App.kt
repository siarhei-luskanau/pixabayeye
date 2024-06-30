package siarhei.luskanau.pixabayeye.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.addLastModifiedToFileCacheKey
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor.KtorNetworkFetcherFactory
import kotlinx.serialization.Serializable
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
fun App(
    viewModelProvider: () -> AppViewModel,
    dispatcherSet: DispatcherSet,
    navController: NavHostController = rememberNavController()
) = AppTheme {
    val appViewModel = viewModel { viewModelProvider.invoke() }

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

    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()

    @OptIn(ExperimentalMaterial3Api::class)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            when (backStackEntry?.destination?.route) {
                                AppRoutes.Splash::class.qualifiedName -> Res.string.screen_name_splash
                                AppRoutes.Search::class.qualifiedName -> Res.string.screen_name_search
                                AppRoutes.Login::class.qualifiedName -> Res.string.screen_name_login
                                AppRoutes.Details::class.qualifiedName -> Res.string.screen_name_search
                                else -> Res.string.screen_name_search
                            }
                        )
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                navigationIcon = {
                    if (backStackEntry?.destination?.route == AppRoutes.Details::class.qualifiedName) {
                        IconButton(onClick = { navController.navigate(route = AppRoutes.Details) }) {
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
                    when (backStackEntry?.destination?.route) {
                        AppRoutes.Details::class.qualifiedName,
                        AppRoutes.Search::class.qualifiedName -> {
                            IconButton(
                                onClick = { navController.navigate(route = AppRoutes.Search) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = null
                                )
                            }
                        }

                        AppRoutes.Login::class.qualifiedName,
                        AppRoutes.Splash::class.qualifiedName -> Unit
                    }

                    when (backStackEntry?.destination?.route) {
                        AppRoutes.Details::class.qualifiedName,
                        AppRoutes.Login::class.qualifiedName,
                        AppRoutes.Search::class.qualifiedName -> {
                            IconButton(
                                onClick = { navController.navigate(route = AppRoutes.Login) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AccountBox,
                                    contentDescription = null
                                )
                            }
                        }

                        AppRoutes.Splash::class.qualifiedName -> Unit
                    }
                }
            )
        }
    ) { contentPadding ->
        NavHost(
            navController = navController,
            startDestination = AppRoutes.Splash
        ) {
            composable<AppRoutes.Splash> {
                SplashComposable(
                    onSplashComplete = {
                        when (appViewModel.splashVewModel.isApiKeyOk()) {
                            is NetworkResult.Failure -> navController.navigate(route = AppRoutes.Login)
                            is NetworkResult.Success -> navController.navigate(route = AppRoutes.Search)
                        }
                    },
                    modifier = Modifier.padding(contentPadding)
                )
            }
            composable<AppRoutes.Login> {
                val loginVewModel = appViewModel.createLoginVewModel {
                    navController.navigate(route = AppRoutes.Search)
                }
                LoginComposable(
                    loginVewState = loginVewModel.getLoginVewState(),
                    modifier = Modifier.padding(contentPadding),
                    onInit = { loginVewModel.onInit() },
                    onUpdateClick = { apiKey -> loginVewModel.onUpdateClick(apiKey) },
                    onCheckClick = { loginVewModel.onCheckClick() }
                )
            }
            composable<AppRoutes.Search> {
                SearchComposable(
                    searchVewStateFlow = appViewModel.searchVewModel.getSearchVewStateFlow(),
                    onUpdateSearchTerm = { searchTerm ->
                        appViewModel.searchVewModel.onUpdateSearchTerm(searchTerm = searchTerm)
                    },
                    onImageClicked = { hitModel ->
                        navController.navigate(route = AppRoutes.Details)
                    },
                    modifier = Modifier.padding(contentPadding)
                )
            }
            composable<AppRoutes.Details> {
                val hitModel: HitModel = backStackEntry?.arguments?.get("hitModel") as HitModel
                DetailsComposable(
                    hitModel = hitModel,
                    modifier = Modifier.padding(contentPadding)
                )
            }
        }
    }
}

@Serializable
internal sealed interface AppRoutes {
    @Serializable data object Splash : AppRoutes
    @Serializable data object Search : AppRoutes
    @Serializable data object Login : AppRoutes
    @Serializable data object Details : AppRoutes
}
