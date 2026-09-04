package siarhei.luskanau.pixabayeye.navigation

import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.addLastModifiedToFileCacheKey
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.getKoin
import org.koin.core.parameter.parametersOf
import siarhei.luskanau.pixabayeye.common.theme.AppTheme
import siarhei.luskanau.pixabayeye.core.common.DispatcherSet
import siarhei.luskanau.pixabayeye.core.common.MediaType
import siarhei.luskanau.pixabayeye.ui.common.resources.Res
import siarhei.luskanau.pixabayeye.ui.common.resources.no_media_selected
import siarhei.luskanau.pixabayeye.ui.common.resources.screen_name_search
import siarhei.luskanau.pixabayeye.ui.debug.debugGraph
import siarhei.luskanau.pixabayeye.ui.media.details.MediaDetailsNavigationCallback
import siarhei.luskanau.pixabayeye.ui.media.details.MediaDetailsScreen
import siarhei.luskanau.pixabayeye.ui.media.list.MediaListNavigationCallback
import siarhei.luskanau.pixabayeye.ui.media.list.MediaListScreen

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
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
    val appNavigation = koin.get<AppNavigation>()
    val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>()
    val selectedMediaType = when (val currentRoute = appNavigation.backStack.lastOrNull()) {
        is AppRoutes.MediaList -> currentRoute.mediaType
        is AppRoutes.MediaDetails -> currentRoute.mediaType
        else -> null
    }
    AppNavigationSuiteScaffold(
        selectedMediaType = selectedMediaType,
        onImagesClick = {
            appNavigation.backStack.add(
                AppRoutes.MediaList(
                    searchTerm = appNavigation.currentSearchTerm,
                    mediaType = MediaType.IMAGE
                )
            )
        },
        onVideosClick = {
            appNavigation.backStack.add(
                AppRoutes.MediaList(
                    searchTerm = appNavigation.currentSearchTerm,
                    mediaType = MediaType.VIDEO
                )
            )
        },
        title = stringResource(Res.string.screen_name_search),
        onBackClick = { appNavigation.goBack() },
        onDebugScreenClick = { appNavigation.onDebugScreenClicked() }
    ) {
        NavDisplay(
            backStack = appNavigation.backStack,
            onBack = { appNavigation.goBack() },
            sceneStrategies = listOf(listDetailStrategy),
            entryProvider = entryProvider {
                entry<AppRoutes.MediaList>(
                    metadata = ListDetailSceneStrategy.listPane(
                        detailPlaceholder = { Text(stringResource(Res.string.no_media_selected)) }
                    )
                ) { route ->
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
                        }
                    )
                }
                entry<AppRoutes.MediaDetails>(
                    metadata = ListDetailSceneStrategy.detailPane()
                ) { route ->
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
}

internal sealed interface AppRoutes : NavKey {

    @Serializable
    data class MediaList(val searchTerm: String?, val mediaType: MediaType) : AppRoutes

    @Serializable
    data class MediaDetails(val id: Long, val mediaType: MediaType) : AppRoutes
}
