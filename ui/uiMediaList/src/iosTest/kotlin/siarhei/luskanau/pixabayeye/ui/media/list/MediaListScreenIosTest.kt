package siarhei.luskanau.pixabayeye.ui.media.list

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.v2.runComposeUiTest
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test
import siarhei.luskanau.pixabayeye.core.network.api.HitModel
import siarhei.luskanau.pixabayeye.core.stub.resources.HIT_LIST
import siarhei.luskanau.pixabayeye.core.stub.resources.TYPES_IMAGE
import siarhei.luskanau.pixabayeye.core.stub.resources.TYPES_VIDEO

@OptIn(ExperimentalTestApi::class, ExperimentalRoborazziApi::class)
class MediaListScreenIosTest {

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
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.media.list." +
                "MediaListScreenIosTest.lightImageRefreshIsLoading.png"
        )
    }

    @Test
    fun lightImageRefreshIsError() = runComposeUiTest {
        setContent { MediaListContentImageRefreshIsErrorPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.media.list." +
                "MediaListScreenIosTest.lightImageRefreshIsError.png"
        )
    }

    @Test
    fun lightImageDataPresentAndNotLoading() = runComposeUiTest {
        setContent { MediaListContentImageDataPresentAndNotLoadingPreview(hitList = imageHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.media.list." +
                "MediaListScreenIosTest.lightImageDataPresentAndNotLoading.png"
        )
    }

    @Test
    fun lightImageDataAbsentAndNotLoading() = runComposeUiTest {
        setContent { MediaListContentImageDataAbsentAndNotLoadingPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.media.list." +
                "MediaListScreenIosTest.lightImageDataAbsentAndNotLoading.png"
        )
    }

    @Test
    fun lightImagePrependLoading() = runComposeUiTest {
        setContent { MediaListContentImagePrependLoadingPreview(hitList = imageHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.media.list." +
                "MediaListScreenIosTest.lightImagePrependLoading.png"
        )
    }

    @Test
    fun lightImagePrependError() = runComposeUiTest {
        setContent { MediaListContentImagePrependErrorPreview(hitList = imageHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.media.list." +
                "MediaListScreenIosTest.lightImagePrependError.png"
        )
    }

    @Test
    fun lightImageAppendLoading() = runComposeUiTest {
        setContent { MediaListContentImageAppendLoadingPreview(hitList = imageHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.media.list." +
                "MediaListScreenIosTest.lightImageAppendLoading.png"
        )
    }

    @Test
    fun lightImageAppendError() = runComposeUiTest {
        setContent { MediaListContentImageAppendErrorPreview(hitList = imageHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.media.list." +
                "MediaListScreenIosTest.lightImageAppendError.png"
        )
    }

    @Test
    fun lightVideoRefreshIsLoading() = runComposeUiTest {
        setContent { MediaListContentVideoRefreshIsLoadingPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.media.list." +
                "MediaListScreenIosTest.lightVideoRefreshIsLoading.png"
        )
    }

    @Test
    fun lightVideoRefreshIsError() = runComposeUiTest {
        setContent { MediaListContentVideoRefreshIsErrorPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.media.list." +
                "MediaListScreenIosTest.lightVideoRefreshIsError.png"
        )
    }

    @Test
    fun lightVideoDataPresentAndNotLoading() = runComposeUiTest {
        setContent { MediaListContentVideoDataPresentAndNotLoadingPreview(hitList = videoHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.media.list." +
                "MediaListScreenIosTest.lightVideoDataPresentAndNotLoading.png"
        )
    }

    @Test
    fun lightVideoDataAbsentAndNotLoading() = runComposeUiTest {
        setContent { MediaListContentVideoDataAbsentAndNotLoadingPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.media.list." +
                "MediaListScreenIosTest.lightVideoDataAbsentAndNotLoading.png"
        )
    }

    @Test
    fun lightVideoPrependLoading() = runComposeUiTest {
        setContent { MediaListContentVideoPrependLoadingPreview(hitList = videoHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.media.list." +
                "MediaListScreenIosTest.lightVideoPrependLoading.png"
        )
    }

    @Test
    fun lightVideoPrependError() = runComposeUiTest {
        setContent { MediaListContentVideoPrependErrorPreview(hitList = videoHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.media.list." +
                "MediaListScreenIosTest.lightVideoPrependError.png"
        )
    }

    @Test
    fun lightVideoAppendLoading() = runComposeUiTest {
        setContent { MediaListContentVideoAppendLoadingPreview(hitList = videoHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.media.list." +
                "MediaListScreenIosTest.lightVideoAppendLoading.png"
        )
    }

    @Test
    fun lightVideoAppendError() = runComposeUiTest {
        setContent { MediaListContentVideoAppendErrorPreview(hitList = videoHitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.media.list." +
                "MediaListScreenIosTest.lightVideoAppendError.png"
        )
    }
}
