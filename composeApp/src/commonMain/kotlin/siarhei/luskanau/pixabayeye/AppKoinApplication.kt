package siarhei.luskanau.pixabayeye

import org.koin.core.annotation.KoinApplication
import siarhei.luskanau.pixabayeye.core.common.CoreCommonCommonModule
import siarhei.luskanau.pixabayeye.core.network.CoreNetworkModule
import siarhei.luskanau.pixabayeye.core.pref.CorePrefCommonModule
import siarhei.luskanau.pixabayeye.ui.debug.UiDebugModule
import siarhei.luskanau.pixabayeye.ui.media.details.UiMediaDetailsModule
import siarhei.luskanau.pixabayeye.ui.media.list.UiMediaListModule

@KoinApplication(
    modules = [
        AppDiModule::class,
        CoreCommonCommonModule::class,
        CoreNetworkModule::class,
        CorePrefCommonModule::class,
        UiDebugModule::class,
        UiMediaDetailsModule::class,
        UiMediaListModule::class
    ]
)
internal class AppKoinApplication
