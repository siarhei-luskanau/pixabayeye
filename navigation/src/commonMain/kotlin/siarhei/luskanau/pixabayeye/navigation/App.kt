package siarhei.luskanau.pixabayeye.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.addLastModifiedToFileCacheKey
import kotlinx.serialization.Serializable
import org.koin.compose.getKoin
import org.koin.core.parameter.parametersOf
import siarhei.luskanau.pixabayeye.common.theme.AppTheme
import siarhei.luskanau.pixabayeye.core.common.DispatcherSet
import siarhei.luskanau.pixabayeye.ui.debug.debugGraph
import siarhei.luskanau.pixabayeye.ui.details.DetailsComposable
import siarhei.luskanau.pixabayeye.ui.search.SearchComposable

@Composable
fun App() = AppTheme {
    val koin = getKoin()
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
        startDestination = AppRoutes.Search
    ) {
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
        debugGraph(koin = koin)
    }
}

internal sealed interface AppRoutes {

    @Serializable
    data object Search : AppRoutes

    @Serializable
    data class Details(val imageId: Long) : AppRoutes
}
