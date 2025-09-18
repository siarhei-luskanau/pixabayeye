package siarhei.luskanau.pixabayeye

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.module.Module
import org.koin.dsl.KoinConfiguration
import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.core.common.coreCommonModule
import siarhei.luskanau.pixabayeye.core.network.coreNetworkModule
import siarhei.luskanau.pixabayeye.core.pref.corePrefModule
import siarhei.luskanau.pixabayeye.navigation.App
import siarhei.luskanau.pixabayeye.ui.debug.uiDebugModule
import siarhei.luskanau.pixabayeye.ui.image.details.ImageDetailsViewModel
import siarhei.luskanau.pixabayeye.ui.image.list.ImageListViewModel
import siarhei.luskanau.pixabayeye.ui.video.details.VideoDetailsViewModel
import siarhei.luskanau.pixabayeye.ui.video.list.VideoListViewModel

@Preview
@Composable
fun KoinApp() = KoinMultiplatformApplication(
    config = KoinConfiguration {
        modules(
            appModule,
            appPlatformModule,
            coreCommonModule,
            coreNetworkModule,
            corePrefModule,
            uiDebugModule
        )
    }
) {
    App()
}

expect val appPlatformModule: Module

val appModule by lazy {
    module {
        factory {
            ImageDetailsViewModel(
                imageId = it[0],
                detailsNavigationCallback = it[1],
                pixabayApiService = get()
            )
        }
        factory {
            ImageListViewModel(
                imageListNavigationCallback = it[0],
                initialSearchTerm = it[1],
                pixabayApiService = get()
            )
        }
        factory {
            VideoDetailsViewModel(
                videoId = it[0],
                videoDetailsNavigationCallback = it[1],
                pixabayApiService = get()
            )
        }
        factory {
            VideoListViewModel(
                videoListNavigationCallback = it[0],
                initialSearchTerm = it[1],
                pixabayApiService = get()
            )
        }
    }
}
