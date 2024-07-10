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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import coil3.ImageLoader
import coil3.addLastModifiedToFileCacheKey
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor.KtorNetworkFetcherFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer
import org.jetbrains.compose.resources.stringResource
import siarhei.luskanau.pixabayeye.core.common.DispatcherSet
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
    appViewModel: AppViewModel,
    dispatcherSet: DispatcherSet,
    navController: NavHostController = rememberNavController()
) = AppTheme {
    @OptIn(ExperimentalCoilApi::class)
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .coroutineContext(dispatcherSet.ioDispatcher())
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
                            when {
                                checkRoute<AppRoutes.Splash>(
                                    backStackEntry?.destination?.route
                                ) -> Res.string.screen_name_splash
                                checkRoute<AppRoutes.Search>(
                                    backStackEntry?.destination?.route
                                ) -> Res.string.screen_name_search
                                checkRoute<AppRoutes.Login>(
                                    backStackEntry?.destination?.route
                                ) -> Res.string.screen_name_login
                                checkRoute<AppRoutes.Details>(
                                    backStackEntry?.destination?.route
                                ) -> Res.string.screen_name_search
                                else -> Res.string.screen_name_search
                            }
                        )
                    )
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                navigationIcon = {
                    if (checkRoute<AppRoutes.Details>(backStackEntry?.destination?.route)) {
                        IconButton(onClick = {
                            navController.navigate(route = AppRoutes.Search)
                        }) {
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
                    when {
                        checkRoute<AppRoutes.Details>(backStackEntry?.destination?.route) ||
                            checkRoute<AppRoutes.Search>(backStackEntry?.destination?.route)
                        -> {
                            IconButton(
                                onClick = { navController.navigate(route = AppRoutes.Search) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = null
                                )
                            }
                        }
                    }

                    when {
                        checkRoute<AppRoutes.Details>(backStackEntry?.destination?.route) ||
                            checkRoute<AppRoutes.Login>(backStackEntry?.destination?.route) ||
                            checkRoute<AppRoutes.Search>(backStackEntry?.destination?.route)
                        -> {
                            IconButton(
                                onClick = { navController.navigate(route = AppRoutes.Login) }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AccountBox,
                                    contentDescription = null
                                )
                            }
                        }
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
                            is NetworkResult.Failure ->
                                navController.navigate(route = AppRoutes.Login)
                            is NetworkResult.Success ->
                                navController.navigate(route = AppRoutes.Search)
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
                        navController.navigate(
                            route = AppRoutes.Details(
                                largeImageUrl = hitModel.largeImageUrl,
                                tags = hitModel.tags
                            )
                        )
                    },
                    modifier = Modifier.padding(contentPadding)
                )
            }
            composable<AppRoutes.Details> {
                val args: AppRoutes.Details = it.toRoute()
                DetailsComposable(
                    largeImageUrl = args.largeImageUrl,
                    tags = args.tags,
                    modifier = Modifier.padding(contentPadding)
                )
            }
        }
    }
}

internal sealed interface AppRoutes {
    @Serializable data object Splash : AppRoutes

    @Serializable data object Search : AppRoutes

    @Serializable data object Login : AppRoutes

    @Serializable data class Details(val largeImageUrl: String, val tags: String) : AppRoutes
}

@OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
private inline fun <reified T : Any> checkRoute(route: String?): Boolean =
    T::class.serializer().descriptor.serialName
        .let { route.orEmpty().startsWith(it) }
