package siarhei.luskanau.pixabayeye

import androidx.compose.runtime.Composable
import org.koin.compose.KoinMultiplatformApplication
import org.koin.dsl.KoinConfiguration
import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.di.allModules
import siarhei.luskanau.pixabayeye.navigation.App

@Composable
fun KoinApp() = KoinMultiplatformApplication(
    config = KoinConfiguration {
        modules(
            *allModules(
                appModule = module {}
            ).toTypedArray()
        )
    }
) {
    App()
}
