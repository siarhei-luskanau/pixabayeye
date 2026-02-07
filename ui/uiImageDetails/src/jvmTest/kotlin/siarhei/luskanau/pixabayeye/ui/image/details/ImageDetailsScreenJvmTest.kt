package siarhei.luskanau.pixabayeye.ui.image.details

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runDesktopComposeUiTest
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test
import siarhei.luskanau.pixabayeye.core.network.api.HitModel
import siarhei.luskanau.pixabayeye.core.stub.resources.HIT_LIST
import siarhei.luskanau.pixabayeye.core.stub.resources.TYPES_IMAGE

@OptIn(ExperimentalTestApi::class)
class ImageDetailsScreenJvmTest {

    private val stubData: HitModel by lazy { HIT_LIST.first { TYPES_IMAGE.contains(it.type) } }

    @Test
    fun lightLoading() = runDesktopComposeUiTest {
        setContent { ImageDetailsLoadingContentPreview() }
        onRoot().captureRoboImage()
    }

    @Test
    fun lightSuccess() = runDesktopComposeUiTest {
        setContent { ImageDetailsSuccessContentPreview(hitModel = stubData) }
        onRoot().captureRoboImage()
    }

    @Test
    fun lightError() = runDesktopComposeUiTest {
        setContent { ImageDetailsErrorContentPreview() }
        onRoot().captureRoboImage()
    }
}
