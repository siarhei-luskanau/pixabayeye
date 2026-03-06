package siarhei.luskanau.pixabayeye.common

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.v2.runComposeUiTest
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@OptIn(ExperimentalTestApi::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = RobolectricDeviceQualifiers.SmallPhone)
class PixabayBottomBarAndroidTest {

    @Test
    fun lightImages() = runComposeUiTest {
        setContent { PixabayBottomBarImagesPreview() }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightImages() = runComposeUiTest {
        setContent { PixabayBottomBarImagesPreview() }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightVideos() = runComposeUiTest {
        setContent { PixabayBottomBarVideosPreview() }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightVideos() = runComposeUiTest {
        setContent { PixabayBottomBarVideosPreview() }
        waitForIdle()
        onRoot().captureRoboImage()
    }
}
