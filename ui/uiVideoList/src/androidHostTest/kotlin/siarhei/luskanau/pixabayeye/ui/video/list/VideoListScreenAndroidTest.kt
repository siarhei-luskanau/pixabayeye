package siarhei.luskanau.pixabayeye.ui.video.list

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.captureRoboImage
import kotlin.test.Test
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import siarhei.luskanau.pixabayeye.core.network.api.HitModel
import siarhei.luskanau.pixabayeye.core.stub.resources.HIT_LIST
import siarhei.luskanau.pixabayeye.core.stub.resources.TYPES_VIDEO

@GraphicsMode(GraphicsMode.Mode.NATIVE)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36], qualifiers = RobolectricDeviceQualifiers.SmallPhone)
class VideoListScreenAndroidTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val hitList: List<HitModel> by lazy {
        HIT_LIST.filter { TYPES_VIDEO.contains(it.type) }
    }

    @Test
    fun lightRefreshIsLoading() {
        composeRule.setContent { VideoListContentRefreshIsLoadingPreview() }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightRefreshIsLoading() {
        composeRule.setContent { VideoListContentRefreshIsLoadingPreview() }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    fun lightRefreshIsError() {
        composeRule.setContent { VideoListContentRefreshIsErrorPreview() }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightRefreshIsError() {
        composeRule.setContent { VideoListContentRefreshIsErrorPreview() }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    fun lightDataPresentAndNotLoading() {
        composeRule.setContent {
            VideoListContentDataPresentAndNotLoadingPreview(hitList = hitList)
        }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightDataPresentAndNotLoading() {
        composeRule.setContent {
            VideoListContentDataPresentAndNotLoadingPreview(hitList = hitList)
        }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    fun lightDataAbsentAndNotLoading() {
        composeRule.setContent { VideoListContentDataAbsentAndNotLoadingPreview() }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightDataAbsentAndNotLoading() {
        composeRule.setContent { VideoListContentDataAbsentAndNotLoadingPreview() }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    fun lightPrependLoading() {
        composeRule.setContent { VideoListContentPrependLoadingPreview(hitList = hitList) }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightPrependLoading() {
        composeRule.setContent { VideoListContentPrependLoadingPreview(hitList = hitList) }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    fun lightPrependError() {
        composeRule.setContent { VideoListContentPrependErrorPreview(hitList = hitList) }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightPrependError() {
        composeRule.setContent { VideoListContentPrependErrorPreview(hitList = hitList) }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    fun lightAppendLoading() {
        composeRule.setContent { VideoListContentAppendLoadingPreview(hitList = hitList) }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightAppendLoading() {
        composeRule.setContent { VideoListContentAppendLoadingPreview(hitList = hitList) }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    fun lightAppendError() {
        composeRule.setContent { VideoListContentAppendErrorPreview(hitList = hitList) }
        composeRule.onRoot().captureRoboImage()
    }

    @Test
    @Config(qualifiers = "+night")
    fun nightAppendError() {
        composeRule.setContent { VideoListContentAppendErrorPreview(hitList = hitList) }
        composeRule.onRoot().captureRoboImage()
    }
}
