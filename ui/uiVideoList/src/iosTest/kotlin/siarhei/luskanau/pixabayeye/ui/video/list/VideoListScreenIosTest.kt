package siarhei.luskanau.pixabayeye.ui.video.list

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runComposeUiTest
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test
import siarhei.luskanau.pixabayeye.core.network.api.HitModel
import siarhei.luskanau.pixabayeye.core.stub.resources.HIT_LIST
import siarhei.luskanau.pixabayeye.core.stub.resources.TYPES_VIDEO

@OptIn(ExperimentalTestApi::class, ExperimentalRoborazziApi::class)
class VideoListScreenIosTest {

    private val hitList: List<HitModel> by lazy {
        HIT_LIST.filter { TYPES_VIDEO.contains(it.type) }
    }

    @Test
    fun lightRefreshIsLoading() = runComposeUiTest {
        setContent { VideoListContentRefreshIsLoadingPreview() }
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.video.list." +
                "VideoListScreenIosTest.lightRefreshIsLoading.png"
        )
    }

    @Test
    fun lightRefreshIsError() = runComposeUiTest {
        setContent { VideoListContentRefreshIsErrorPreview() }
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.video.list." +
                "VideoListScreenIosTest.lightRefreshIsError.png"
        )
    }

    @Test
    fun lightDataPresentAndNotLoading() = runComposeUiTest {
        setContent { VideoListContentDataPresentAndNotLoadingPreview(hitList = hitList) }
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.video.list." +
                "VideoListScreenIosTest.lightDataPresentAndNotLoading.png"
        )
    }

    @Test
    fun lightDataAbsentAndNotLoading() = runComposeUiTest {
        setContent { VideoListContentDataAbsentAndNotLoadingPreview() }
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.video.list." +
                "VideoListScreenIosTest.lightDataAbsentAndNotLoading.png"
        )
    }

    @Test
    fun lightPrependLoading() = runComposeUiTest {
        setContent { VideoListContentPrependLoadingPreview(hitList = hitList) }
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.video.list." +
                "VideoListScreenIosTest.lightPrependLoading.png"
        )
    }

    @Test
    fun lightPrependError() = runComposeUiTest {
        setContent { VideoListContentPrependErrorPreview(hitList = hitList) }
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.video.list." +
                "VideoListScreenIosTest.lightPrependError.png"
        )
    }

    @Test
    fun lightAppendLoading() = runComposeUiTest {
        setContent { VideoListContentAppendLoadingPreview(hitList = hitList) }
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.video.list." +
                "VideoListScreenIosTest.lightAppendLoading.png"
        )
    }

    @Test
    fun lightAppendError() = runComposeUiTest {
        setContent { VideoListContentAppendErrorPreview(hitList = hitList) }
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.video.list." +
                "VideoListScreenIosTest.lightAppendError.png"
        )
    }
}
