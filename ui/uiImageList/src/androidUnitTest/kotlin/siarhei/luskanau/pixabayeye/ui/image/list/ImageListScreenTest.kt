package siarhei.luskanau.pixabayeye.ui.image.list

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
import siarhei.luskanau.pixabayeye.core.network.api.HitModel
import siarhei.luskanau.pixabayeye.core.stub.resources.HIT_LIST
import siarhei.luskanau.pixabayeye.core.stub.resources.TYPES_IMAGE
import siarhei.luskanau.pixabayeye.ui.screenshot.test.BaseScreenshotTest

@GraphicsMode(GraphicsMode.Mode.NATIVE)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34], qualifiers = RobolectricDeviceQualifiers.SmallPhone)
class ImageListScreenTest : BaseScreenshotTest(group = "image_list") {

    @get:Rule
    val composeRule = createComposeRule()

    private val hitList: List<HitModel> by lazy {
        HIT_LIST.filter { TYPES_IMAGE.contains(it.type) }
    }

    @BeforeTest
    fun setup() {
        setupAndroidContextProvider()
    }

    @Test
    fun lightTest() {
        composeRule.setContent { ImageListContentPreview(hitList = hitList) }
        composeRule.onRoot().captureScreenshot(name = "light")
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightTest() {
        composeRule.setContent { ImageListContentPreview(hitList = hitList) }
        composeRule.onRoot().captureScreenshot(name = "night")
    }

    @Test
    fun dumpTest() {
        composeRule.setContent { ImageListContentPreview(hitList = hitList) }
        composeRule.onRoot().captureScreenshotDump(name = "dump")
    }
}
