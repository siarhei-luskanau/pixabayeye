import androidx.compose.ui.window.ComposeUIViewController
import org.koin.core.Koin
import platform.UIKit.UIViewController
import siarhei.luskanau.pixabayeye.navigation.App

fun mainViewController(koin: Koin): UIViewController = ComposeUIViewController(
    configure = { enforceStrictPlistSanityCheck = false }
) {
    App(koin = koin)
}
