package siarhei.luskanau.pixabayeye.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import siarhei.luskanau.pixabayeye.network.di.networkModule
import siarhei.luskanau.pixabayeye.pref.di.prefModule
import siarhei.luskanau.pixabayeye.ui.di.uiModule

fun initKoin(appModule: Module): KoinApplication =
    startKoin {
        modules(
            appModule,
            networkModule,
            platformModule,
            prefModule,
            uiModule,
        )
    }

expect val platformModule: Module
