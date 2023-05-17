package siarhei.luskanau.compose.multiplatform.pixabayeye.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initKoin(appModule: Module): KoinApplication =
    startKoin {
        modules(
            appModule,
            sharedCommonModule,
            platformModule,
        )
    }

val sharedCommonModule = module {}

expect val platformModule: Module
