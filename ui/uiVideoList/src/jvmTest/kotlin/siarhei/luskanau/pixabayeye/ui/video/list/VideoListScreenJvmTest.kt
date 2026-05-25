package siarhei.luskanau.pixabayeye.ui.video.list

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.v2.runComposeUiTest
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test
import siarhei.luskanau.pixabayeye.core.network.api.HitModel
import siarhei.luskanau.pixabayeye.core.stub.resources.HIT_LIST
import siarhei.luskanau.pixabayeye.core.stub.resources.TYPES_VIDEO

@OptIn(ExperimentalTestApi::class)
class VideoListScreenJvmTest {

    private val hitList: List<HitModel> by lazy {
        HIT_LIST.filter { TYPES_VIDEO.contains(it.type) }
    }

    @Test
    fun lightRefreshIsLoading() = runComposeUiTest {
        setContent { VideoListContentRefreshIsLoadingPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightRefreshIsError() = runComposeUiTest {
        setContent { VideoListContentRefreshIsErrorPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightDataPresentAndNotLoading() = runComposeUiTest {
        setContent { VideoListContentDataPresentAndNotLoadingPreview(hitList = hitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightDataAbsentAndNotLoading() = runComposeUiTest {
        setContent { VideoListContentDataAbsentAndNotLoadingPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightPrependLoading() = runComposeUiTest {
        setContent { VideoListContentPrependLoadingPreview(hitList = hitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightPrependError() = runComposeUiTest {
        setContent { VideoListContentPrependErrorPreview(hitList = hitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightAppendLoading() = runComposeUiTest {
        setContent { VideoListContentAppendLoadingPreview(hitList = hitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightAppendError() = runComposeUiTest {
        setContent { VideoListContentAppendErrorPreview(hitList = hitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }
}
