package siarhei.luskanau.pixabayeye.common

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@GraphicsMode(GraphicsMode.Mode.NATIVE)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = RobolectricDeviceQualifiers.SmallPhone)
class PixabayBottomBarTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun lightImagesTest() {
        composeRule.setContent { PixabayBottomBarImagesPreview() }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightImagesTest() {
        composeRule.setContent { PixabayBottomBarImagesPreview() }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    fun lightVideosTest() {
        composeRule.setContent { PixabayBottomBarVideosPreview() }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightVideosTest() {
        composeRule.setContent { PixabayBottomBarVideosPreview() }
        composeRule.onRoot().captureRoboImage()
    }
}
