package siarhei.luskanau.pixabayeye.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.ksp.generated.module
import siarhei.luskanau.pixabayeye.core.common.CoreCommonModule
import siarhei.luskanau.pixabayeye.core.network.CoreNetworkModule
import siarhei.luskanau.pixabayeye.core.pref.CorePrefModule
import siarhei.luskanau.pixabayeye.ui.details.UiDetailsModule
import siarhei.luskanau.pixabayeye.ui.login.UiLoginModule
import siarhei.luskanau.pixabayeye.ui.search.UiSearchModule
import siarhei.luskanau.pixabayeye.ui.splash.UiSplashModule

fun initKoin(appModule: Module): KoinApplication = startKoin {
    modules(
        appModule,
        appPlatformModule,
        CoreCommonModule().module,
        CoreNetworkModule().module,
        CorePrefModule().module,
        UiDetailsModule().module,
        UiLoginModule().module,
        UiSearchModule().module,
        UiSplashModule().module
    )
}

expect val appPlatformModule: Module
