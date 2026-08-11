package siarhei.luskanau.pixabayeye.ui.debug

import org.koin.core.annotation.KoinApplication
import siarhei.luskanau.pixabayeye.core.common.CoreCommonCommonModule
import siarhei.luskanau.pixabayeye.core.network.CoreNetworkModule
import siarhei.luskanau.pixabayeye.core.pref.CorePrefCommonModule

@KoinApplication(
    modules = [
        CoreCommonCommonModule::class,
        CoreNetworkModule::class,
        CorePrefCommonModule::class,
        UiDebugModule::class
    ]
)
internal class DebugKoinApplication
