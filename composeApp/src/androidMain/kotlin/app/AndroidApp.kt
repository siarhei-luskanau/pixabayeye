package app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.androix.startup.KoinStartup
import org.koin.dsl.KoinConfiguration
import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.di.allModules

class AndroidApp :
    Application(),
    KoinStartup {

    override fun onCreate() {
        super.onCreate()
        // getKoin().get<PlatformService>().setStrictMode(isEnabled = true)
    }

    override fun onKoinStartup() = KoinConfiguration {
        modules(
            *allModules(
                appModule = module {
                    androidContext(this@AndroidApp)
                }
            ).toTypedArray()
        )
    }
}
