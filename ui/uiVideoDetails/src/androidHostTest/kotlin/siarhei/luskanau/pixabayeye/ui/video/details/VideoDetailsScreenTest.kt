package siarhei.luskanau.pixabayeye.ui.video.details

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
import siarhei.luskanau.pixabayeye.core.network.api.HitModel
import siarhei.luskanau.pixabayeye.core.stub.resources.HIT_LIST
import siarhei.luskanau.pixabayeye.core.stub.resources.TYPES_VIDEO

@GraphicsMode(GraphicsMode.Mode.NATIVE)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = RobolectricDeviceQualifiers.SmallPhone)
class VideoDetailsScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val stubData: HitModel by lazy { HIT_LIST.first { TYPES_VIDEO.contains(it.type) } }

    @Test
    fun lightLoadingTest() {
        composeRule.setContent { VideoDetailsLoadingContentPreview() }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightLoadingTest() {
        composeRule.setContent { VideoDetailsLoadingContentPreview() }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    fun lightSuccessTest() {
        composeRule.setContent { VideoDetailsSuccessContentPreview(hitModel = stubData) }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightSuccessTest() {
        composeRule.setContent { VideoDetailsSuccessContentPreview(hitModel = stubData) }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    fun lightErrorTest() {
        composeRule.setContent { VideoDetailsErrorContentPreview() }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightErrorTest() {
        composeRule.setContent { VideoDetailsErrorContentPreview() }
        composeRule.onRoot().captureRoboImage()
    }
}
