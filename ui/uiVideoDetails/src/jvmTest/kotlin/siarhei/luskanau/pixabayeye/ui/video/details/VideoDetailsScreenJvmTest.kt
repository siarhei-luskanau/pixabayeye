package siarhei.luskanau.pixabayeye.ui.video.details

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runDesktopComposeUiTest
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test
import siarhei.luskanau.pixabayeye.core.network.api.HitModel
import siarhei.luskanau.pixabayeye.core.stub.resources.HIT_LIST
import siarhei.luskanau.pixabayeye.core.stub.resources.TYPES_VIDEO

@OptIn(ExperimentalTestApi::class)
class VideoDetailsScreenJvmTest {

    private val stubData: HitModel by lazy { HIT_LIST.first { TYPES_VIDEO.contains(it.type) } }

    @Test
    fun lightLoading() = runDesktopComposeUiTest {
        setContent { VideoDetailsLoadingContentPreview() }
        onRoot().captureRoboImage()
    }

    @Test
    fun lightSuccess() = runDesktopComposeUiTest {
        setContent { VideoDetailsSuccessContentPreview(hitModel = stubData) }
        onRoot().captureRoboImage()
    }

    @Test
    fun lightError() = runDesktopComposeUiTest {
        setContent { VideoDetailsErrorContentPreview() }
        onRoot().captureRoboImage()
    }
}
