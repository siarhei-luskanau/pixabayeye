package app

import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.koin.dsl.module
import siarhei.luskanau.compose.multiplatform.pixabayeye.App
import siarhei.luskanau.compose.multiplatform.pixabayeye.di.initKoin

class AndroidApp : Application()

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(
                koin = initKoin(
                    module {
                        single<Context> { applicationContext }
                    },
                ).koin,
            )
        }
    }
}
