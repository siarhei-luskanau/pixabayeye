package app

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.di.initKoin
import siarhei.luskanau.pixabayeye.ui.App

class AndroidApp : Application()

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(
                appViewModel = initKoin(
                    module {
                        single<Context> { applicationContext }
                    },
                ).koin.get(),
            )
        }
    }
}
