package siarhei.luskanau.pixabayeye.ui.image.list

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
class ImageListScreenAndroidTest {

    private val hitList: List<HitModel> by lazy {
        HIT_LIST.filter { TYPES_IMAGE.contains(it.type) }
    }

    @Test
    fun lightRefreshIsLoading() = runComposeUiTest {
        setContent { ImageListContentRefreshIsLoadingPreview() }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightRefreshIsLoading() = runComposeUiTest {
        setContent { ImageListContentRefreshIsLoadingPreview() }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightRefreshIsError() = runComposeUiTest {
        setContent { ImageListContentRefreshIsErrorPreview() }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightRefreshIsError() = runComposeUiTest {
        setContent { ImageListContentRefreshIsErrorPreview() }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightDataPresentAndNotLoading() = runComposeUiTest {
        setContent { ImageListContentDataPresentAndNotLoadingPreview(hitList = hitList) }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightDataPresentAndNotLoading() = runComposeUiTest {
        setContent { ImageListContentDataPresentAndNotLoadingPreview(hitList = hitList) }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightDataAbsentAndNotLoading() = runComposeUiTest {
        setContent { ImageListContentDataAbsentAndNotLoadingPreview() }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightDataAbsentAndNotLoading() = runComposeUiTest {
        setContent { ImageListContentDataAbsentAndNotLoadingPreview() }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightPrependLoading() = runComposeUiTest {
        setContent { ImageListContentPrependLoadingPreview(hitList = hitList) }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightPrependLoading() = runComposeUiTest {
        setContent { ImageListContentPrependLoadingPreview(hitList = hitList) }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightPrependError() = runComposeUiTest {
        setContent { ImageListContentPrependErrorPreview(hitList = hitList) }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightPrependError() = runComposeUiTest {
        setContent { ImageListContentPrependErrorPreview(hitList = hitList) }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightAppendLoading() = runComposeUiTest {
        setContent { ImageListContentAppendLoadingPreview(hitList = hitList) }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightAppendLoading() = runComposeUiTest {
        setContent { ImageListContentAppendLoadingPreview(hitList = hitList) }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightAppendError() = runComposeUiTest {
        setContent { ImageListContentAppendErrorPreview(hitList = hitList) }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightAppendError() = runComposeUiTest {
        setContent { ImageListContentAppendErrorPreview(hitList = hitList) }
        waitForIdle()
        onRoot().captureRoboImage()
    }
}
