package siarhei.luskanau.pixabayeye.ui.media.details

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
import siarhei.luskanau.pixabayeye.core.stub.resources.TYPES_VIDEO

@OptIn(ExperimentalTestApi::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = RobolectricDeviceQualifiers.SmallPhone)
class MediaDetailsScreenAndroidTest {

    private val imageHitModel: HitModel by lazy {
        HIT_LIST.first { TYPES_IMAGE.contains(it.type) }
    }
    private val videoHitModel: HitModel by lazy {
        HIT_LIST.first { TYPES_VIDEO.contains(it.type) }
    }

    @Test
    fun lightImageLoading() = runComposeUiTest {
        setContent { MediaDetailsImageLoadingPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightImageLoading() = runComposeUiTest {
        setContent { MediaDetailsImageLoadingPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightImageSuccess() = runComposeUiTest {
        setContent { MediaDetailsImageSuccessPreview(hitModel = imageHitModel) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightImageSuccess() = runComposeUiTest {
        setContent { MediaDetailsImageSuccessPreview(hitModel = imageHitModel) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightImageError() = runComposeUiTest {
        setContent { MediaDetailsImageErrorPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightImageError() = runComposeUiTest {
        setContent { MediaDetailsImageErrorPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightVideoLoading() = runComposeUiTest {
        setContent { MediaDetailsVideoLoadingPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightVideoLoading() = runComposeUiTest {
        setContent { MediaDetailsVideoLoadingPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightVideoSuccess() = runComposeUiTest {
        setContent { MediaDetailsVideoSuccessPreview(hitModel = videoHitModel) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightVideoSuccess() = runComposeUiTest {
        setContent { MediaDetailsVideoSuccessPreview(hitModel = videoHitModel) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightVideoError() = runComposeUiTest {
        setContent { MediaDetailsVideoErrorPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightVideoError() = runComposeUiTest {
        setContent { MediaDetailsVideoErrorPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }
}
