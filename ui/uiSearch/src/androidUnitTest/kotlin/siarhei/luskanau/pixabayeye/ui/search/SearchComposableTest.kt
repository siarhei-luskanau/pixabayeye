package siarhei.luskanau.pixabayeye.ui.search

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
class SearchComposableTest : BaseScreenshotTest(group = "search") {

    @get:Rule
    val composeRule = createComposeRule()

    @BeforeTest
    fun setup() {
        setupAndroidContextProvider()
    }

    @Test
    fun lightTest() {
        composeRule.setContent { SearchComposablePreview() }
        composeRule.onRoot().captureScreenshot(name = "light")
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightTest() {
        composeRule.setContent { SearchComposablePreview() }
        composeRule.onRoot().captureScreenshot(name = "night")
    }

    @Test
    fun dumpTest() {
        composeRule.setContent { SearchComposablePreview() }
        composeRule.onRoot().captureScreenshotDump(name = "dump")
    }
}
