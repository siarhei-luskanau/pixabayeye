package siarhei.luskanau.pixabayeye.common.paging

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.v2.runComposeUiTest
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class ErrorContentJvmTest {
    @Test
    fun light() = runComposeUiTest {
        setContent { ErrorContentPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }
}
