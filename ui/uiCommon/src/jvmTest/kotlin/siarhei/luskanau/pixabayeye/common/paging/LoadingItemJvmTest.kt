package siarhei.luskanau.pixabayeye.common.paging

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.runDesktopComposeUiTest
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class LoadingItemJvmTest {
    @Test
    fun light() = runDesktopComposeUiTest {
        setContent { LoadingItemPreview() }
        onRoot().captureRoboImage()
    }
}
