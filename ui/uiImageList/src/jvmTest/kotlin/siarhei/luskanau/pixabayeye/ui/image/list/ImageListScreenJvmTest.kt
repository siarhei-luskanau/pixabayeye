package siarhei.luskanau.pixabayeye.ui.image.list

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runDesktopComposeUiTest
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
    fun lightRefreshIsLoading() = runDesktopComposeUiTest {
        setContent { ImageListContentRefreshIsLoadingPreview() }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightRefreshIsError() = runDesktopComposeUiTest {
        setContent { ImageListContentRefreshIsErrorPreview() }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightDataPresentAndNotLoading() = runDesktopComposeUiTest {
        setContent { ImageListContentDataPresentAndNotLoadingPreview(hitList = hitList) }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightDataAbsentAndNotLoading() = runDesktopComposeUiTest {
        setContent { ImageListContentDataAbsentAndNotLoadingPreview() }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightPrependLoading() = runDesktopComposeUiTest {
        setContent { ImageListContentPrependLoadingPreview(hitList = hitList) }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightPrependError() = runDesktopComposeUiTest {
        setContent { ImageListContentPrependErrorPreview(hitList = hitList) }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightAppendLoading() = runDesktopComposeUiTest {
        setContent { ImageListContentAppendLoadingPreview(hitList = hitList) }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightAppendError() = runDesktopComposeUiTest {
        setContent { ImageListContentAppendErrorPreview(hitList = hitList) }
        waitForIdle()
        onRoot().captureRoboImage()
    }
}
