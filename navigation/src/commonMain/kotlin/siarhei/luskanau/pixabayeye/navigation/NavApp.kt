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
import siarhei.luskanau.pixabayeye.core.common.MediaType
import siarhei.luskanau.pixabayeye.ui.debug.debugGraph
import siarhei.luskanau.pixabayeye.ui.media.details.MediaDetailsNavigationCallback
import siarhei.luskanau.pixabayeye.ui.media.details.MediaDetailsScreen
import siarhei.luskanau.pixabayeye.ui.media.list.MediaListNavigationCallback
import siarhei.luskanau.pixabayeye.ui.media.list.MediaListScreen

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
    val backStack = mutableStateListOf<NavKey>(
        AppRoutes.MediaList(searchTerm = null, mediaType = MediaType.IMAGE)
    )
    val appNavigation = AppNavigation(backStack = backStack)
    NavDisplay(
        backStack = backStack,
        onBack = { if (backStack.size > 1) backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<AppRoutes.MediaList> { route ->
                MediaListScreen(
                    key = "MediaList_${route.mediaType}_${route.searchTerm}",
                    mediaType = route.mediaType,
                    viewModelProvider = {
                        koin.get {
                            parametersOf(
                                route.mediaType,
                                appNavigation as MediaListNavigationCallback,
                                route.searchTerm
                            )
                        }
                    },
                    onImagesClick = { searchTerm ->
                        backStack.add(
                            AppRoutes.MediaList(
                                searchTerm = searchTerm,
                                mediaType = MediaType.IMAGE
                            )
                        )
                    },
                    onVideosClick = { searchTerm ->
                        backStack.add(
                            AppRoutes.MediaList(
                                searchTerm = searchTerm,
                                mediaType = MediaType.VIDEO
                            )
                        )
                    }
                )
            }
            entry<AppRoutes.MediaDetails> { route ->
                MediaDetailsScreen(
                    key = "MediaDetails_${route.mediaType}_${route.id}",
                    viewModelProvider = {
                        koin.get {
                            parametersOf(
                                route.mediaType,
                                route.id,
                                appNavigation as MediaDetailsNavigationCallback
                            )
                        }
                    }
                )
            }
            debugGraph(koin = koin)
        }
    )
}

internal sealed interface AppRoutes : NavKey {

    @Serializable
    data class MediaList(val searchTerm: String?, val mediaType: MediaType) : AppRoutes

    @Serializable
    data class MediaDetails(val id: Long, val mediaType: MediaType) : AppRoutes
}
