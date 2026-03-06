package siarhei.luskanau.pixabayeye.common

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.v2.runComposeUiTest
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@OptIn(ExperimentalTestApi::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = RobolectricDeviceQualifiers.SmallPhone)
class PixabayTopAppBarAndroidTest {

    @Test
    fun light() = runComposeUiTest {
        setContent { PixabayTopAppBarPreview() }
        waitForIdle()
        onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun night() = runComposeUiTest {
        setContent { PixabayTopAppBarPreview() }
        waitForIdle()
        onRoot().captureRoboImage()
    }
}
