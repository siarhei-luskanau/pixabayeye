package siarhei.luskanau.pixabayeye.ui.image.details

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
class ImageDetailsScreenIosTest {

    private val stubData: HitModel by lazy { HIT_LIST.first { TYPES_IMAGE.contains(it.type) } }

    @Test
    fun lightLoading() = runComposeUiTest {
        setContent { ImageDetailsLoadingContentPreview() }
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.image.details." +
                "ImageDetailsScreenIosTest.lightLoading.png"
        )
    }

    @Test
    fun lightSuccess() = runComposeUiTest {
        setContent { ImageDetailsSuccessContentPreview(hitModel = stubData) }
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.image.details." +
                "ImageDetailsScreenIosTest.lightSuccess.png"
        )
    }

    @Test
    fun lightError() = runComposeUiTest {
        setContent { ImageDetailsErrorContentPreview() }
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.ui.image.details." +
                "ImageDetailsScreenIosTest.lightError.png"
        )
    }
}
