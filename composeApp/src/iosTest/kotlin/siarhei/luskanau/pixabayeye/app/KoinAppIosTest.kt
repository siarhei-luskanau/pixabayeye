package siarhei.luskanau.pixabayeye.app

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.v2.runComposeUiTest
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class, ExperimentalRoborazziApi::class)
class KoinAppIosTest {
    @Test
    fun preview() = runComposeUiTest {
        setContent { KoinApp() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.app.KoinAppIosTest.preview.png"
        )
    }
}
