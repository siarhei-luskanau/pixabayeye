package siarhei.luskanau.pixabayeye

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runComposeUiTest
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class, ExperimentalRoborazziApi::class)
class KoinAppIosTest {
    @Test
    fun preview() = runComposeUiTest {
        setContent { KoinApp() }
        onRoot().captureRoboImage(
            this,
            filePath = "siarhei.luskanau.pixabayeye.KoinAppIosTest.preview.png"
        )
    }
}
