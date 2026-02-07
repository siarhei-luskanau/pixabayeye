package siarhei.luskanau.pixabayeye.common

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runDesktopComposeUiTest
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class PixabayBottomBarJvmTest {
    @Test
    fun lightImages() = runDesktopComposeUiTest {
        setContent { PixabayBottomBarImagesPreview() }
        onRoot().captureRoboImage()
    }

    @Test
    fun lightVideos() = runDesktopComposeUiTest {
        setContent { PixabayBottomBarVideosPreview() }
        onRoot().captureRoboImage()
    }
}
