package siarhei.luskanau.pixabayeye.ui.common

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.v2.runComposeUiTest
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class, ExperimentalRoborazziApi::class)
class PixabayTopAppBarIosTest {
    @Test
    fun light() = runComposeUiTest {
        setContent { PixabayTopAppBarPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.common.PixabayTopAppBarIosTest.light.png"
        )
    }
}
