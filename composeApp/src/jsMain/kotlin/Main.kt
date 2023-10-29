import org.jetbrains.skiko.wasm.onWasmReady
import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.di.initKoin
import siarhei.luskanau.pixabayeye.navigation.App

fun main() {
    onWasmReady {
        BrowserViewportWindow("PixabayEye") {
            App(
                appViewModel =
                initKoin(
                    module {}
                ).koin.get()
            )
        }
    }
}
