package siarhei.luskanau.pixabayeye.ui.image.details

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
class ImageDetailsScreenTest : BaseScreenshotTest(group = "image_details") {

    @get:Rule
    val composeRule = createComposeRule()

    @BeforeTest
    fun setup() {
        setupAndroidContextProvider()
    }

    @Test
    fun lightLoadingTest() {
        composeRule.setContent { ImageDetailsLoadingContentPreview() }
        composeRule.onRoot().captureScreenshot(name = "loading_light")
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightLoadingTest() {
        composeRule.setContent { ImageDetailsLoadingContentPreview() }
        composeRule.onRoot().captureScreenshot(name = "loading_night")
    }

    @Test
    fun dumpLoadingTest() {
        composeRule.setContent { ImageDetailsLoadingContentPreview() }
        composeRule.onRoot().captureScreenshotDump(name = "loading_dump")
    }

    @Test
    fun lightSuccessTest() {
        composeRule.setContent { ImageDetailsSuccessContentPreview() }
        composeRule.onRoot().captureScreenshot(name = "success_light")
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightSuccessTest() {
        composeRule.setContent { ImageDetailsSuccessContentPreview() }
        composeRule.onRoot().captureScreenshot(name = "success_night")
    }

    @Test
    fun dumpSuccessTest() {
        composeRule.setContent { ImageDetailsSuccessContentPreview() }
        composeRule.onRoot().captureScreenshotDump(name = "success_dump")
    }

    @Test
    fun lightErrorTest() {
        composeRule.setContent { ImageDetailsErrorContentPreview() }
        composeRule.onRoot().captureScreenshot(name = "error_light")
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightErrorTest() {
        composeRule.setContent { ImageDetailsErrorContentPreview() }
        composeRule.onRoot().captureScreenshot(name = "error_night")
    }

    @Test
    fun dumpErrorTest() {
        composeRule.setContent { ImageDetailsErrorContentPreview() }
        composeRule.onRoot().captureScreenshotDump(name = "error_dump")
    }
}
