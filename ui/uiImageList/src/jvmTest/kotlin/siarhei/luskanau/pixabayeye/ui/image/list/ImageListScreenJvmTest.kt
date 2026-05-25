package siarhei.luskanau.pixabayeye.ui.image.list

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.v2.runComposeUiTest
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test
import siarhei.luskanau.pixabayeye.core.network.api.HitModel
import siarhei.luskanau.pixabayeye.core.stub.resources.HIT_LIST
import siarhei.luskanau.pixabayeye.core.stub.resources.TYPES_IMAGE

@OptIn(ExperimentalTestApi::class)
class ImageListScreenJvmTest {

    private val hitList: List<HitModel> by lazy {
        HIT_LIST.filter { TYPES_IMAGE.contains(it.type) }
    }

    @Test
    fun lightRefreshIsLoading() = runComposeUiTest {
        setContent { ImageListContentRefreshIsLoadingPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightRefreshIsError() = runComposeUiTest {
        setContent { ImageListContentRefreshIsErrorPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightDataPresentAndNotLoading() = runComposeUiTest {
        setContent { ImageListContentDataPresentAndNotLoadingPreview(hitList = hitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightDataAbsentAndNotLoading() = runComposeUiTest {
        setContent { ImageListContentDataAbsentAndNotLoadingPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightPrependLoading() = runComposeUiTest {
        setContent { ImageListContentPrependLoadingPreview(hitList = hitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightPrependError() = runComposeUiTest {
        setContent { ImageListContentPrependErrorPreview(hitList = hitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightAppendLoading() = runComposeUiTest {
        setContent { ImageListContentAppendLoadingPreview(hitList = hitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightAppendError() = runComposeUiTest {
        setContent { ImageListContentAppendErrorPreview(hitList = hitList) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }
}
