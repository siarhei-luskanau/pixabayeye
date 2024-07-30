package siarhei.luskanau.pixabayeye.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
import siarhei.luskanau.pixabayeye.core.common.DispatcherSet
import siarhei.luskanau.pixabayeye.core.network.NetworkResult
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
            .components { add(KtorNetworkFetcherFactory()) }
            .addLastModifiedToFileCacheKey(false)
            .build()
    }

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
                }
            )
        }
        composable<AppRoutes.Login> {
            val loginVewModel = appViewModel.createLoginVewModel {
                navController.navigate(route = AppRoutes.Search)
            }
            LoginComposable(
                loginVewState = loginVewModel.getLoginVewState(),
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
                onHomeClick = { navController.navigate(route = AppRoutes.Search) },
                onLoginClick = { navController.navigate(route = AppRoutes.Login) }
            )
        }
        composable<AppRoutes.Details> {
            val args: AppRoutes.Details = it.toRoute()
            DetailsComposable(
                largeImageUrl = args.largeImageUrl,
                tags = args.tags,
                onBackClick = { navController.navigateUp() }
            )
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
private inline fun <reified T : Any> checkRoute(backStackEntry: NavBackStackEntry?): Boolean =
    T::class.serializer().descriptor.serialName
        .let { backStackEntry?.destination?.route.orEmpty().startsWith(it) }
