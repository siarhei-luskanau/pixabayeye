import org.jetbrains.skiko.wasm.onWasmReady
import org.koin.dsl.module
import siarhei.luskanau.pixabayeye.di.initKoin
import siarhei.luskanau.pixabayeye.ui.App

fun main() {
    onWasmReady {
        BrowserViewportWindow("Compose App") {
            App(
                appViewModel = initKoin(
                    module {},
                ).koin.get(),
            )
        }
    }
}
