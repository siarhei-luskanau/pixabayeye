package siarhei.luskanau.pixabayeye.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import siarhei.luskanau.pixabayeye.core.di.coreModule
import siarhei.luskanau.pixabayeye.core.di.corePlatformModule
import siarhei.luskanau.pixabayeye.network.di.networkModule
import siarhei.luskanau.pixabayeye.pref.di.prefModule
import siarhei.luskanau.pixabayeye.ui.di.uiModule

fun initKoin(appModule: Module): KoinApplication =
    startKoin {
        modules(
            appModule,
            coreModule,
            corePlatformModule,
            networkModule,
            platformModule,
            prefModule,
            uiModule,
        )
    }

expect val platformModule: Module
