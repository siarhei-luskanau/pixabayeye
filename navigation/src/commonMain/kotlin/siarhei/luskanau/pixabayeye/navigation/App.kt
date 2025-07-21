package siarhei.luskanau.pixabayeye.navigation

import androidx.compose.runtime.Composable
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
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin
import org.koin.core.parameter.parametersOf
import siarhei.luskanau.pixabayeye.common.theme.AppTheme
import siarhei.luskanau.pixabayeye.core.common.DispatcherSet
import siarhei.luskanau.pixabayeye.ui.debug.debugGraph
import siarhei.luskanau.pixabayeye.ui.image.details.ImageDetailsScreen
import siarhei.luskanau.pixabayeye.ui.image.list.ImageListScreen

@Preview
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
            ImageListScreen { koin.get { parametersOf(appNavigation) } }
        }
        composable<AppRoutes.Details> {
            val args: AppRoutes.Details = it.toRoute()
            ImageDetailsScreen {
                koin.get { parametersOf(args.imageId, appNavigation) }
            }
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
