package siarhei.luskanau.pixabayeye.ui.debug

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateListOf
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import org.koin.android.ext.android.getKoin
import org.koin.compose.KoinApplication
import org.koin.dsl.KoinConfiguration
import org.koin.plugin.module.dsl.koinConfiguration
import siarhei.luskanau.pixabayeye.common.theme.AppTheme

class DebugActivity : ComponentActivity() {

    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                KoinApplication(
                    configuration = KoinConfiguration {
                        koinConfiguration<DebugKoinApplication>().config.invoke(this)
                    }
                ) {
                    val koin = getKoin()
                    val backStack = mutableStateListOf<NavKey>(DebugGraph)
                    NavDisplay(
                        backStack = backStack,
                        onBack = { backStack.removeLastOrNull() },
                        entryProvider = entryProvider {
                            debugGraph(koin = koin)
                        }
                    )
                }
            }
        }
    }
}
