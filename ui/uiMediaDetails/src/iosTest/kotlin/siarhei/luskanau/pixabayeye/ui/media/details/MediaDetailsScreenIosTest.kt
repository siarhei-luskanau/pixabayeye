package siarhei.luskanau.pixabayeye.ui.media.details

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
class MediaDetailsScreenIosTest {

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
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.media.details." +
                "MediaDetailsScreenIosTest.lightImageLoading.png"
        )
    }

    @Test
    fun lightImageSuccess() = runComposeUiTest {
        setContent { MediaDetailsImageSuccessPreview(hitModel = imageHitModel) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.media.details." +
                "MediaDetailsScreenIosTest.lightImageSuccess.png"
        )
    }

    @Test
    fun lightImageError() = runComposeUiTest {
        setContent { MediaDetailsImageErrorPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.media.details." +
                "MediaDetailsScreenIosTest.lightImageError.png"
        )
    }

    @Test
    fun lightVideoLoading() = runComposeUiTest {
        setContent { MediaDetailsVideoLoadingPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.media.details." +
                "MediaDetailsScreenIosTest.lightVideoLoading.png"
        )
    }

    @Test
    fun lightVideoSuccess() = runComposeUiTest {
        setContent { MediaDetailsVideoSuccessPreview(hitModel = videoHitModel) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.media.details." +
                "MediaDetailsScreenIosTest.lightVideoSuccess.png"
        )
    }

    @Test
    fun lightVideoError() = runComposeUiTest {
        setContent { MediaDetailsVideoErrorPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.media.details." +
                "MediaDetailsScreenIosTest.lightVideoError.png"
        )
    }
}
