package siarhei.luskanau.pixabayeye.di

import org.koin.core.module.Module
import org.koin.ksp.generated.module
import siarhei.luskanau.pixabayeye.core.common.CoreCommonModule
import siarhei.luskanau.pixabayeye.core.network.CoreNetworkModule
import siarhei.luskanau.pixabayeye.core.pref.CorePrefModule
import siarhei.luskanau.pixabayeye.ui.details.UiDetailsModule
import siarhei.luskanau.pixabayeye.ui.search.UiSearchModule

fun allModules(appModule: Module): List<Module> = listOf(
    appModule,
    appPlatformModule,
    CoreCommonModule().module,
    CoreNetworkModule().module,
    CorePrefModule().module,
    UiDetailsModule().module,
    UiSearchModule().module
)

expect val appPlatformModule: Module
