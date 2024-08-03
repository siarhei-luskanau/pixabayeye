package siarhei.luskanau.pixabayeye.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.ksp.generated.module
import siarhei.luskanau.pixabayeye.core.common.di.CoreCommonModule
import siarhei.luskanau.pixabayeye.core.network.di.coreNetworkModule
import siarhei.luskanau.pixabayeye.core.pref.di.corePrefModule
import siarhei.luskanau.pixabayeye.navigation.di.navigationModule
import siarhei.luskanau.pixabayeye.navigation.di.uiModules

fun initKoin(appModule: Module): KoinApplication = startKoin {
    modules(
        appModule,
        appPlatformModule,
        CoreCommonModule().module,
        coreNetworkModule,
        corePrefModule,
        navigationModule
    )
    modules(uiModules)
}

expect val appPlatformModule: Module
