import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.koin.dsl.module
import siarhei.luskanau.compose.multiplatform.pixabayeye.App
import siarhei.luskanau.compose.multiplatform.pixabayeye.di.initKoin

fun main() = application {
    Window(
        title = "Compose App",
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication,
    ) {
        App(
            koin = initKoin(
                module {},
            ).koin,
        )
    }
}
