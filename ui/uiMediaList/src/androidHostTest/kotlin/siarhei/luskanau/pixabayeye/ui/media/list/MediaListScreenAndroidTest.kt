package siarhei.luskanau.pixabayeye.ui.media.list

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
class MediaListScreenAndroidTest {

    private val imageHitList: List<HitModel> by lazy {
        HIT_LIST.filter { TYPES_IMAGE.contains(it.type) }
    }
    private val videoHitList: List<HitModel> by lazy {
        HIT_LIST.filter { TYPES_VIDEO.contains(it.type) }
    }

    @Test
    fun lightImageRefreshIsLoading() = runComposeUiTest {
        setContent { MediaListContentImageRefreshIsLoadingPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightImageRefreshIsLoading() = runComposeUiTest {
        setContent { MediaListContentImageRefreshIsLoadingPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightImageRefreshIsError() = runComposeUiTest {
        setContent { MediaListContentImageRefreshIsErrorPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightImageRefreshIsError() = runComposeUiTest {
        setContent { MediaListContentImageRefreshIsErrorPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightImageDataPresentAndNotLoading() = runComposeUiTest {
        setContent { MediaListContentImageDataPresentAndNotLoadingPreview(hitList = imageHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightImageDataPresentAndNotLoading() = runComposeUiTest {
        setContent { MediaListContentImageDataPresentAndNotLoadingPreview(hitList = imageHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightImageDataAbsentAndNotLoading() = runComposeUiTest {
        setContent { MediaListContentImageDataAbsentAndNotLoadingPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightImageDataAbsentAndNotLoading() = runComposeUiTest {
        setContent { MediaListContentImageDataAbsentAndNotLoadingPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightImagePrependLoading() = runComposeUiTest {
        setContent { MediaListContentImagePrependLoadingPreview(hitList = imageHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightImagePrependLoading() = runComposeUiTest {
        setContent { MediaListContentImagePrependLoadingPreview(hitList = imageHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightImagePrependError() = runComposeUiTest {
        setContent { MediaListContentImagePrependErrorPreview(hitList = imageHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightImagePrependError() = runComposeUiTest {
        setContent { MediaListContentImagePrependErrorPreview(hitList = imageHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightImageAppendLoading() = runComposeUiTest {
        setContent { MediaListContentImageAppendLoadingPreview(hitList = imageHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightImageAppendLoading() = runComposeUiTest {
        setContent { MediaListContentImageAppendLoadingPreview(hitList = imageHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightImageAppendError() = runComposeUiTest {
        setContent { MediaListContentImageAppendErrorPreview(hitList = imageHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightImageAppendError() = runComposeUiTest {
        setContent { MediaListContentImageAppendErrorPreview(hitList = imageHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightVideoRefreshIsLoading() = runComposeUiTest {
        setContent { MediaListContentVideoRefreshIsLoadingPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightVideoRefreshIsLoading() = runComposeUiTest {
        setContent { MediaListContentVideoRefreshIsLoadingPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightVideoRefreshIsError() = runComposeUiTest {
        setContent { MediaListContentVideoRefreshIsErrorPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightVideoRefreshIsError() = runComposeUiTest {
        setContent { MediaListContentVideoRefreshIsErrorPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightVideoDataPresentAndNotLoading() = runComposeUiTest {
        setContent { MediaListContentVideoDataPresentAndNotLoadingPreview(hitList = videoHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightVideoDataPresentAndNotLoading() = runComposeUiTest {
        setContent { MediaListContentVideoDataPresentAndNotLoadingPreview(hitList = videoHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightVideoDataAbsentAndNotLoading() = runComposeUiTest {
        setContent { MediaListContentVideoDataAbsentAndNotLoadingPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightVideoDataAbsentAndNotLoading() = runComposeUiTest {
        setContent { MediaListContentVideoDataAbsentAndNotLoadingPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightVideoPrependLoading() = runComposeUiTest {
        setContent { MediaListContentVideoPrependLoadingPreview(hitList = videoHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightVideoPrependLoading() = runComposeUiTest {
        setContent { MediaListContentVideoPrependLoadingPreview(hitList = videoHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightVideoPrependError() = runComposeUiTest {
        setContent { MediaListContentVideoPrependErrorPreview(hitList = videoHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightVideoPrependError() = runComposeUiTest {
        setContent { MediaListContentVideoPrependErrorPreview(hitList = videoHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightVideoAppendLoading() = runComposeUiTest {
        setContent { MediaListContentVideoAppendLoadingPreview(hitList = videoHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightVideoAppendLoading() = runComposeUiTest {
        setContent { MediaListContentVideoAppendLoadingPreview(hitList = videoHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightVideoAppendError() = runComposeUiTest {
        setContent { MediaListContentVideoAppendErrorPreview(hitList = videoHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightVideoAppendError() = runComposeUiTest {
        setContent { MediaListContentVideoAppendErrorPreview(hitList = videoHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }
}
