package siarhei.luskanau.pixabayeye.ui.common

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.v2.runComposeUiTest
import io.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class PixabayTopAppBarJvmTest {
    @Test
    fun light() = runComposeUiTest {
        setContent { PixabayTopAppBarPreview() }
        waitForIdle()
        awaitIdle()
        onRoot().captureRoboImage()
    }
}
