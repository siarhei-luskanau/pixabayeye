package siarhei.luskanau.pixabayeye.common

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import kotlin.test.BeforeTest
import kotlin.test.Test
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import siarhei.luskanau.pixabayeye.ui.screenshot.test.BaseScreenshotTest

@GraphicsMode(GraphicsMode.Mode.NATIVE)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], qualifiers = RobolectricDeviceQualifiers.SmallPhone)
class PixabayBottomBarTest : BaseScreenshotTest(group = "common_bottombar") {

    @get:Rule
    val composeRule = createComposeRule()

    @BeforeTest
    fun setup() {
        setupAndroidContextProvider()
    }

    @Test
    fun lightImagesTest() {
        composeRule.setContent { PixabayBottomBarImagesPreview() }
        composeRule.onRoot().captureScreenshot(name = "light")
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightImagesTest() {
        composeRule.setContent { PixabayBottomBarImagesPreview() }
        composeRule.onRoot().captureScreenshot(name = "night")
    }

    @Test
    fun dumpImagesTest() {
        composeRule.setContent { PixabayBottomBarImagesPreview() }
        composeRule.onRoot().captureScreenshotDump(name = "dump")
    }

    @Test
    fun lightVideosTest() {
        composeRule.setContent { PixabayBottomBarVideosPreview() }
        composeRule.onRoot().captureScreenshot(name = "light")
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightVideosTest() {
        composeRule.setContent { PixabayBottomBarVideosPreview() }
        composeRule.onRoot().captureScreenshot(name = "night")
    }

    @Test
    fun dumpVideosTest() {
        composeRule.setContent { PixabayBottomBarVideosPreview() }
        composeRule.onRoot().captureScreenshotDump(name = "dump")
    }
}
