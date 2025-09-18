package siarhei.luskanau.pixabayeye.ui.debug

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.runBlocking
import okio.Path
import okio.Path.Companion.toPath
import org.koin.android.ext.android.getKoin
import org.koin.compose.KoinMultiplatformApplication
import org.koin.dsl.KoinConfiguration
import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.common.theme.AppTheme
import siarhei.luskanau.pixabayeye.core.common.DispatcherSet
import siarhei.luskanau.pixabayeye.core.common.coreCommonModule
import siarhei.luskanau.pixabayeye.core.network.coreNetworkModule
import siarhei.luskanau.pixabayeye.core.pref.PrefPathProvider
import siarhei.luskanau.pixabayeye.core.pref.corePrefModule

class DebugActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                KoinMultiplatformApplication(
                    config = KoinConfiguration {
                        modules(
                            coreCommonModule,
                            coreNetworkModule,
                            corePrefModule,
                            uiDebugModule,
                            module {
                                single<PrefPathProvider> {
                                    val context: Context = get()
                                    val dispatcherSet: DispatcherSet = get()
                                    object : PrefPathProvider {
                                        override fun get(): Path =
                                            runBlocking(dispatcherSet.ioDispatcher()) {
                                                val file = context.filesDir.resolve("app.pref.json")
                                                file.absolutePath.toPath()
                                            }
                                    }
                                }
                            }
                        )
                    }
                ) {
                    val koin = getKoin()
                    val navHostController: NavHostController = rememberNavController()
                    NavHost(navController = navHostController, startDestination = DebugGraph) {
                        debugGraph(koin = koin)
                    }
                }
            }
        }
    }
}
