package siarhei.luskanau.pixabayeye.common

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runComposeUiTest
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class, ExperimentalRoborazziApi::class)
class PixabayBottomBarIosTest {
    @Test
    fun lightImages() = runComposeUiTest {
        setContent { PixabayBottomBarImagesPreview() }
        onRoot().captureRoboImage(
            this,
            filePath = "siarhei.luskanau.pixabayeye.common.PixabayBottomBarIosTest.lightImages.png"
        )
    }

    @Test
    fun lightVideos() = runComposeUiTest {
        setContent { PixabayBottomBarVideosPreview() }
        onRoot().captureRoboImage(
            this,
            filePath = "siarhei.luskanau.pixabayeye.common.PixabayBottomBarIosTest.lightVideos.png"
        )
    }
}
