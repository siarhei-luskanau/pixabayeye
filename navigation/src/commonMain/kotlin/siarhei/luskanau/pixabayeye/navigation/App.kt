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
import siarhei.luskanau.pixabayeye.ui.video.details.VideoDetailsScreen
import siarhei.luskanau.pixabayeye.ui.video.list.VideoListScreen

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
        startDestination = AppRoutes.ImageList
    ) {
        composable<AppRoutes.ImageList> {
            ImageListScreen(
                viewModelProvider = { koin.get { parametersOf(appNavigation) } },
                onImagesClick = { navHostController.navigate(AppRoutes.ImageList) },
                onVideosClick = { navHostController.navigate(AppRoutes.VideoList) }
            )
        }
        composable<AppRoutes.ImageDetails> {
            val args: AppRoutes.ImageDetails = it.toRoute()
            ImageDetailsScreen {
                koin.get { parametersOf(args.imageId, appNavigation) }
            }
        }
        composable<AppRoutes.VideoList> {
            VideoListScreen(
                viewModelProvider = { koin.get { parametersOf(appNavigation) } },
                onImagesClick = { navHostController.navigate(AppRoutes.ImageList) },
                onVideosClick = { navHostController.navigate(AppRoutes.VideoList) }
            )
        }
        composable<AppRoutes.VideoDetails> {
            val args: AppRoutes.VideoDetails = it.toRoute()
            VideoDetailsScreen {
                koin.get { parametersOf(args.videoId, appNavigation) }
            }
        }
        debugGraph(koin = koin)
    }
}

internal sealed interface AppRoutes {

    @Serializable
    data object ImageList : AppRoutes

    @Serializable
    data class ImageDetails(val imageId: Long) : AppRoutes

    @Serializable
    data object VideoList : AppRoutes

    @Serializable
    data class VideoDetails(val videoId: Long) : AppRoutes
}
