package siarhei.luskanau.pixabayeye.ui.common.paging

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.v2.runComposeUiTest
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class LoadingContentJvmTest {
    @Test
    fun light() = runComposeUiTest {
        setContent { LoadingContentPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }
}
