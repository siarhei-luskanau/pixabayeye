package siarhei.luskanau.pixabayeye.ui.common.paging

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.v2.runComposeUiTest
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class, ExperimentalRoborazziApi::class)
class ErrorItemIosTest {
    @Test
    fun light() = runComposeUiTest {
        setContent { ErrorItemPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.common.paging.ErrorItemIosTest.light.png"
        )
    }
}
