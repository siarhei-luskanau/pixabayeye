package siarhei.luskanau.pixabayeye

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runDesktopComposeUiTest
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class KoinAppJvmTest {
    @Test
    fun preview() = runDesktopComposeUiTest {
        setContent { KoinApp() }
        onRoot().captureRoboImage()
    }
}
