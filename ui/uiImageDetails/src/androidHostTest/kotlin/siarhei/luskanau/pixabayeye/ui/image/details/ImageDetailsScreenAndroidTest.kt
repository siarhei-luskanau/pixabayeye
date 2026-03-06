package siarhei.luskanau.pixabayeye.ui.image.details

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
import siarhei.luskanau.pixabayeye.core.network.api.HitModel
import siarhei.luskanau.pixabayeye.core.stub.resources.HIT_LIST
import siarhei.luskanau.pixabayeye.core.stub.resources.TYPES_IMAGE

@OptIn(ExperimentalTestApi::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = RobolectricDeviceQualifiers.SmallPhone)
class ImageDetailsScreenAndroidTest {

    private val stubData: HitModel by lazy { HIT_LIST.first { TYPES_IMAGE.contains(it.type) } }

    @Test
    fun lightLoading() = runComposeUiTest {
        setContent { ImageDetailsLoadingContentPreview() }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightLoading() = runComposeUiTest {
        setContent { ImageDetailsLoadingContentPreview() }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightSuccess() = runComposeUiTest {
        setContent { ImageDetailsSuccessContentPreview(hitModel = stubData) }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightSuccess() = runComposeUiTest {
        setContent { ImageDetailsSuccessContentPreview(hitModel = stubData) }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightError() = runComposeUiTest {
        setContent { ImageDetailsErrorContentPreview() }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightError() = runComposeUiTest {
        setContent { ImageDetailsErrorContentPreview() }
        waitForIdle()
        onRoot().captureRoboImage()
    }
}
