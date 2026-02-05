package siarhei.luskanau.pixabayeye.common.paging

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runComposeUiTest
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class, ExperimentalRoborazziApi::class)
class LoadingItemIosTest {
    @Test
    fun light() = runComposeUiTest {
        setContent { LoadingItemPreview() }
        onRoot().captureRoboImage(
            composeUiTest = this,
            filePath = "siarhei.luskanau.pixabayeye.common.paging.LoadingItemIosTest.light.png"
        )
    }
}
