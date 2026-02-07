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
    fun light() = runComposeUiTest {
        setContent { ImageListContentPreview(hitList = hitList) }
        onRoot().captureRoboImage(
            this,
            filePath = "siarhei.luskanau.pixabayeye.ui.image.list." +
                "ImageListScreenIosTest.light.png"
        )
    }
}
