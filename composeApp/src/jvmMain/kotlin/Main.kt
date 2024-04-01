import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Dimension
import org.jetbrains.compose.resources.stringResource
import org.koin.dsl.module
import pixabayeye.composeapp.generated.resources.Res
import pixabayeye.composeapp.generated.resources.app_name
import siarhei.luskanau.pixabayeye.di.initKoin
import siarhei.luskanau.pixabayeye.navigation.App

fun main() = application {
    Window(
        title = stringResource(Res.string.app_name),
        state = rememberWindowState(width = 800.dp, height = 600.dp),
        onCloseRequest = ::exitApplication
    ) {
        window.minimumSize = Dimension(350, 600)
        val koin = initKoin(
            module {}
        ).koin
        App(
            appViewModel = koin.get(),
            dispatcherSet = koin.get()
        )
    }
}
