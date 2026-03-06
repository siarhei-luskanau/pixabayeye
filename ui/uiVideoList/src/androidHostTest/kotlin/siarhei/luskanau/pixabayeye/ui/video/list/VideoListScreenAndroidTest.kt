package siarhei.luskanau.pixabayeye.ui.video.list

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
import siarhei.luskanau.pixabayeye.core.stub.resources.TYPES_VIDEO

@OptIn(ExperimentalTestApi::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = RobolectricDeviceQualifiers.SmallPhone)
class VideoListScreenAndroidTest {

    private val hitList: List<HitModel> by lazy {
        HIT_LIST.filter { TYPES_VIDEO.contains(it.type) }
    }

    @Test
    fun lightRefreshIsLoading() = runComposeUiTest {
        setContent { VideoListContentRefreshIsLoadingPreview() }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightRefreshIsLoading() = runComposeUiTest {
        setContent { VideoListContentRefreshIsLoadingPreview() }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightRefreshIsError() = runComposeUiTest {
        setContent { VideoListContentRefreshIsErrorPreview() }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightRefreshIsError() = runComposeUiTest {
        setContent { VideoListContentRefreshIsErrorPreview() }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightDataPresentAndNotLoading() = runComposeUiTest {
        setContent { VideoListContentDataPresentAndNotLoadingPreview(hitList = hitList) }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightDataPresentAndNotLoading() = runComposeUiTest {
        setContent { VideoListContentDataPresentAndNotLoadingPreview(hitList = hitList) }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightDataAbsentAndNotLoading() = runComposeUiTest {
        setContent { VideoListContentDataAbsentAndNotLoadingPreview() }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightDataAbsentAndNotLoading() = runComposeUiTest {
        setContent { VideoListContentDataAbsentAndNotLoadingPreview() }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightPrependLoading() = runComposeUiTest {
        setContent { VideoListContentPrependLoadingPreview(hitList = hitList) }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightPrependLoading() = runComposeUiTest {
        setContent { VideoListContentPrependLoadingPreview(hitList = hitList) }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightPrependError() = runComposeUiTest {
        setContent { VideoListContentPrependErrorPreview(hitList = hitList) }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightPrependError() = runComposeUiTest {
        setContent { VideoListContentPrependErrorPreview(hitList = hitList) }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightAppendLoading() = runComposeUiTest {
        setContent { VideoListContentAppendLoadingPreview(hitList = hitList) }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightAppendLoading() = runComposeUiTest {
        setContent { VideoListContentAppendLoadingPreview(hitList = hitList) }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightAppendError() = runComposeUiTest {
        setContent { VideoListContentAppendErrorPreview(hitList = hitList) }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightAppendError() = runComposeUiTest {
        setContent { VideoListContentAppendErrorPreview(hitList = hitList) }
        waitForIdle()
        onRoot().captureRoboImage()
    }
}
