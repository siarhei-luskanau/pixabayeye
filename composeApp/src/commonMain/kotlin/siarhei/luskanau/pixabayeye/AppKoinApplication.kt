package siarhei.luskanau.pixabayeye

import org.koin.core.annotation.KoinApplication
import siarhei.luskanau.pixabayeye.core.common.CoreCommonCommonModule
import siarhei.luskanau.pixabayeye.core.network.CoreNetworkModule
import siarhei.luskanau.pixabayeye.core.pref.CorePrefCommonModule
import siarhei.luskanau.pixabayeye.ui.debug.UiDebugModule
import siarhei.luskanau.pixabayeye.ui.image.details.UiImageDetailsModule
import siarhei.luskanau.pixabayeye.ui.image.list.UiImageListModule
import siarhei.luskanau.pixabayeye.ui.video.details.UiVideoDetailsModule
import siarhei.luskanau.pixabayeye.ui.video.list.UiVideoListModule

@KoinApplication(
    modules = [
        AppDiModule::class,
        CoreCommonCommonModule::class,
        CoreNetworkModule::class,
        CorePrefCommonModule::class,
        UiDebugModule::class,
        UiImageDetailsModule::class,
        UiImageListModule::class,
        UiVideoDetailsModule::class,
        UiVideoListModule::class
    ]
)
internal class AppKoinApplication
