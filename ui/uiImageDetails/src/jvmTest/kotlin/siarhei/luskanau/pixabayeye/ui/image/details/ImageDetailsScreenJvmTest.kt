package siarhei.luskanau.pixabayeye.ui.image.details

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.v2.runComposeUiTest
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test
import siarhei.luskanau.pixabayeye.core.network.api.HitModel
import siarhei.luskanau.pixabayeye.core.stub.resources.HIT_LIST
import siarhei.luskanau.pixabayeye.core.stub.resources.TYPES_IMAGE

@OptIn(ExperimentalTestApi::class)
class ImageDetailsScreenJvmTest {

    private val stubData: HitModel by lazy { HIT_LIST.first { TYPES_IMAGE.contains(it.type) } }

    @Test
    fun lightLoading() = runComposeUiTest {
        setContent { ImageDetailsLoadingContentPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightSuccess() = runComposeUiTest {
        setContent { ImageDetailsSuccessContentPreview(hitModel = stubData) }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }

    @Test
    fun lightError() = runComposeUiTest {
        setContent { ImageDetailsErrorContentPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }
}
