import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import org.jetbrains.skiko.wasm.onWasmReady
import org.koin.core.context.startKoin
import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.di.allModules
import siarhei.luskanau.pixabayeye.navigation.App

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        CanvasBasedWindow("PixabayEye") {
            val koin = startKoin {
                modules(
                    *allModules(
                        appModule = module {}
                    ).toTypedArray()
                )
            }.koin
            App(koin = koin)
        }
    }
}
