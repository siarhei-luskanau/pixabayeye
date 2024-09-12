package siarhei.luskanau.pixabayeye.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import coil3.ImageLoader
import coil3.addLastModifiedToFileCacheKey
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import kotlinx.serialization.Serializable
import org.koin.core.Koin
import org.koin.core.parameter.parametersOf
import siarhei.luskanau.pixabayeye.core.common.DispatcherSet
import siarhei.luskanau.pixabayeye.ui.details.DetailsComposable
import siarhei.luskanau.pixabayeye.ui.login.LoginComposable
import siarhei.luskanau.pixabayeye.ui.search.SearchComposable
import siarhei.luskanau.pixabayeye.ui.splash.SplashComposable

@Composable
fun App(koin: Koin) = AppTheme {
    @OptIn(ExperimentalCoilApi::class)
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .coroutineContext(koin.get<DispatcherSet>().ioDispatcher())
            .components { add(KtorNetworkFetcherFactory()) }
            .addLastModifiedToFileCacheKey(false)
            .build()
    }
    val navHostController: NavHostController = rememberNavController()
    val appNavigation = AppNavigation(navHostController = navHostController)
    NavHost(
        navController = navHostController,
        startDestination = AppRoutes.Splash
    ) {
        composable<AppRoutes.Splash> {
            SplashComposable(
                viewModel = viewModel { koin.get { parametersOf(appNavigation) } }
            )
        }
        composable<AppRoutes.Login> {
            LoginComposable(
                viewModel = viewModel { koin.get { parametersOf(appNavigation) } }
            )
        }
        composable<AppRoutes.Search> {
            SearchComposable(
                viewModel = viewModel { koin.get { parametersOf(appNavigation) } }
            )
        }
        composable<AppRoutes.Details> {
            val args: AppRoutes.Details = it.toRoute()
            DetailsComposable(
                viewModel = viewModel {
                    koin.get {
                        parametersOf(
                            args.imageId,
                            appNavigation
                        )
                    }
                }
            )
        }
    }
}

internal sealed interface AppRoutes {
    @Serializable
    data object Splash : AppRoutes

    @Serializable
    data object Search : AppRoutes

    @Serializable
    data object Login : AppRoutes

    @Serializable
    data class Details(val imageId: Long) : AppRoutes
}
