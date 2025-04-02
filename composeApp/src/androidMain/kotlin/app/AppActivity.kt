package app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import siarhei.luskanau.pixabayeye.KoinApp

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KoinApp()
        }
    }
}
