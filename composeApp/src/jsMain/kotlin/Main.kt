import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import org.jetbrains.skiko.wasm.onWasmReady
import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.di.initKoin
import siarhei.luskanau.pixabayeye.navigation.App

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        CanvasBasedWindow("PixabayEye") {
            val koin = initKoin(
                module {}
            ).koin
            App(
                appViewModel = koin.get(),
                dispatcherSet = koin.get()
            )
        }
    }
}
