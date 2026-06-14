package siarhei.luskanau.pixabayeye.app

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.v2.runComposeUiTest
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class KoinAppJvmTest {
    @Test
    fun preview() = runComposeUiTest {
        setContent { KoinApp() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }
}
