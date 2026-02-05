package siarhei.luskanau.pixabayeye.ui.image.list

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runComposeUiTest
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test
import siarhei.luskanau.pixabayeye.core.network.api.HitModel
import siarhei.luskanau.pixabayeye.core.stub.resources.HIT_LIST
import siarhei.luskanau.pixabayeye.core.stub.resources.TYPES_IMAGE

@OptIn(ExperimentalTestApi::class, ExperimentalRoborazziApi::class)
class ImageListScreenIosTest {

    private val hitList: List<HitModel> by lazy {
        HIT_LIST.filter { TYPES_IMAGE.contains(it.type) }
    }

    @Test
    fun lightRefreshIsLoading() = runComposeUiTest {
        setContent { ImageListContentRefreshIsLoadingPreview() }
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.image.list." +
                "ImageListScreenIosTest.lightRefreshIsLoading.png"
        )
    }

    @Test
    fun lightRefreshIsError() = runComposeUiTest {
        setContent { ImageListContentRefreshIsErrorPreview() }
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.image.list." +
                "ImageListScreenIosTest.lightRefreshIsError.png"
        )
    }

    @Test
    fun lightDataPresentAndNotLoading() = runComposeUiTest {
        setContent { ImageListContentDataPresentAndNotLoadingPreview(hitList = hitList) }
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.image.list." +
                "ImageListScreenIosTest.lightDataPresentAndNotLoading.png"
        )
    }

    @Test
    fun lightDataAbsentAndNotLoading() = runComposeUiTest {
        setContent { ImageListContentDataAbsentAndNotLoadingPreview() }
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.image.list." +
                "ImageListScreenIosTest.lightDataAbsentAndNotLoading.png"
        )
    }

    @Test
    fun lightPrependLoading() = runComposeUiTest {
        setContent { ImageListContentPrependLoadingPreview(hitList = hitList) }
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.image.list." +
                "ImageListScreenIosTest.lightPrependLoading.png"
        )
    }

    @Test
    fun lightPrependError() = runComposeUiTest {
        setContent { ImageListContentPrependErrorPreview(hitList = hitList) }
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.image.list." +
                "ImageListScreenIosTest.lightPrependError.png"
        )
    }

    @Test
    fun lightAppendLoading() = runComposeUiTest {
        setContent { ImageListContentAppendLoadingPreview(hitList = hitList) }
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.image.list." +
                "ImageListScreenIosTest.lightAppendLoading.png"
        )
    }

    @Test
    fun lightAppendError() = runComposeUiTest {
        setContent { ImageListContentAppendErrorPreview(hitList = hitList) }
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.image.list." +
                "ImageListScreenIosTest.lightAppendError.png"
        )
    }
}
