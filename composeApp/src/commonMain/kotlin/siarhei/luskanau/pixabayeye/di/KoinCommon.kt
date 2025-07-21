package siarhei.luskanau.pixabayeye.di

import org.koin.core.module.Module
import org.koin.ksp.generated.module
import siarhei.luskanau.pixabayeye.core.common.CoreCommonModule
import siarhei.luskanau.pixabayeye.core.network.CoreNetworkModule
import siarhei.luskanau.pixabayeye.core.pref.CorePrefModule
import siarhei.luskanau.pixabayeye.ui.debug.UiDebugModule
import siarhei.luskanau.pixabayeye.ui.image.details.UiImageDetailsModule
import siarhei.luskanau.pixabayeye.ui.image.list.UiImageListModule
import siarhei.luskanau.pixabayeye.ui.video.details.UiVideoDetailsModule
import siarhei.luskanau.pixabayeye.ui.video.list.UiVideoListModule

fun allModules(appModule: Module): List<Module> = listOf(
    appModule,
    appPlatformModule,
    CoreCommonModule().module,
    CoreNetworkModule().module,
    CorePrefModule().module,
    UiDebugModule().module,
    UiImageDetailsModule().module,
    UiImageListModule().module,
    UiVideoListModule().module,
    UiVideoDetailsModule().module
)

expect val appPlatformModule: Module
