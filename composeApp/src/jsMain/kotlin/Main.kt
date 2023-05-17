import org.jetbrains.skiko.wasm.onWasmReady
import org.koin.dsl.module
import siarhei.luskanau.compose.multiplatform.pixabayeye.App
import siarhei.luskanau.compose.multiplatform.pixabayeye.di.initKoin

fun main() {
    onWasmReady {
        BrowserViewportWindow("Compose App") {
            App(
                koin = initKoin(
                    module {},
                ).koin,
            )
        }
    }
}
