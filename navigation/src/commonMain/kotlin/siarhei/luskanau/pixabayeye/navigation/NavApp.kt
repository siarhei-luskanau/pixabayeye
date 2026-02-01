package siarhei.luskanau.pixabayeye.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
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
import siarhei.luskanau.pixabayeye.ui.image.details.ImageDetailsScreen
import siarhei.luskanau.pixabayeye.ui.image.list.ImageListScreen
import siarhei.luskanau.pixabayeye.ui.video.details.VideoDetailsScreen
import siarhei.luskanau.pixabayeye.ui.video.list.VideoListScreen

@Preview
@Composable
fun NavApp() = AppTheme {
    val koin = getKoin()
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .coroutineContext(koin.get<DispatcherSet>().ioDispatcher())
            .components { add(KtorNetworkFetcherFactory()) }
            .addLastModifiedToFileCacheKey(false)
            .build()
    }
    val backStack = mutableStateListOf<NavKey>(AppRoutes.ImageList(searchTerm = null))
    val appNavigation = AppNavigation(backStack = backStack)
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<AppRoutes.ImageList> { route ->
                ImageListScreen(
                    key = "ImageList_${route.searchTerm}",
                    viewModelProvider = {
                        koin.get { parametersOf(appNavigation, route.searchTerm) }
                    },
                    onImagesClick = { searchTerm ->
                        backStack.add(AppRoutes.ImageList(searchTerm = searchTerm))
                    },
                    onVideosClick = { searchTerm ->
                        backStack.add(AppRoutes.VideoList(searchTerm = searchTerm))
                    }
                )
            }
            entry<AppRoutes.ImageDetails> { route ->
                ImageDetailsScreen(key = "ImageDetails_${route.imageId}") {
                    koin.get { parametersOf(route.imageId, appNavigation) }
                }
            }
            entry<AppRoutes.VideoList> { route ->
                VideoListScreen(
                    key = "VideoList_${route.searchTerm}",
                    viewModelProvider = {
                        koin.get { parametersOf(appNavigation, route.searchTerm) }
                    },
                    onImagesClick = { searchTerm ->
                        backStack.add(AppRoutes.ImageList(searchTerm = searchTerm))
                    },
                    onVideosClick = { searchTerm ->
                        backStack.add(AppRoutes.VideoList(searchTerm = searchTerm))
                    }
                )
            }
            entry<AppRoutes.VideoDetails> { route ->
                VideoDetailsScreen(key = "VideoDetails_${route.videoId}") {
                    koin.get { parametersOf(route.videoId, appNavigation) }
                }
            }
            debugGraph(koin = koin)
        }
    )
}

internal sealed interface AppRoutes : NavKey {

    @Serializable
    data class ImageList(val searchTerm: String?) : AppRoutes

    @Serializable
    data class ImageDetails(val imageId: Long) : AppRoutes

    @Serializable
    data class VideoList(val searchTerm: String?) : AppRoutes

    @Serializable
    data class VideoDetails(val videoId: Long) : AppRoutes
}
