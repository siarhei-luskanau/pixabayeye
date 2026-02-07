package siarhei.luskanau.pixabayeye.ui.video.details

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
class VideoDetailsScreenIosTest {

    private val stubData: HitModel by lazy { HIT_LIST.first { TYPES_VIDEO.contains(it.type) } }

    @Test
    fun lightLoading() = runComposeUiTest {
        setContent { VideoDetailsLoadingContentPreview() }
        onRoot().captureRoboImage(
            this,
            filePath = "siarhei.luskanau.pixabayeye.ui.video.details." +
                "VideoDetailsScreenIosTest.lightLoading.png"
        )
    }

    @Test
    fun lightSuccess() = runComposeUiTest {
        setContent { VideoDetailsSuccessContentPreview(hitModel = stubData) }
        onRoot().captureRoboImage(
            this,
            filePath = "siarhei.luskanau.pixabayeye.ui.video.details." +
                "VideoDetailsScreenIosTest.lightSuccess.png"
        )
    }

    @Test
    fun lightError() = runComposeUiTest {
        setContent { VideoDetailsErrorContentPreview() }
        onRoot().captureRoboImage(
            this,
            filePath = "siarhei.luskanau.pixabayeye.ui.video.details." +
                "VideoDetailsScreenIosTest.lightError.png"
        )
    }
}
