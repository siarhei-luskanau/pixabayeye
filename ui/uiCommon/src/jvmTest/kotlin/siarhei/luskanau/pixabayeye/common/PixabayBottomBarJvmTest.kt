package siarhei.luskanau.pixabayeye.common

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.v2.runComposeUiTest
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class PixabayBottomBarJvmTest {
    @Test
    fun lightImages() = runComposeUiTest {
        setContent { PixabayBottomBarImagesPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightVideos() = runComposeUiTest {
        setContent { PixabayBottomBarVideosPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }
}
